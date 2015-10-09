package dms.function;

import common.ADFUtils;
import common.JSFUtils;
import java.util.Iterator;
import java.util.List;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adfinternal.view.faces.model.binding.FacesCtrlListBinding;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;


public class FunctionAuthorityBean {

    private RichTable assignedFunctionTable;
    private RichPopup popup;
    private RichTable unassignedFunctionTable;

    public void roleChangeListener(ValueChangeEvent valueChangeEvent) {
        FacesCtrlListBinding roleName =
            (FacesCtrlListBinding)JSFUtils.resolveExpression("#{bindings.RoleName}");
        roleName.setInputValue(valueChangeEvent.getNewValue());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.assignedFunctionTable);
    }

    public void setAssignedFunctionTable(RichTable assignedFunctionTable) {
        this.assignedFunctionTable = assignedFunctionTable;
    }

    public RichTable getAssignedFunctionTable() {
        return assignedFunctionTable;
    }

    public void showPopup(ActionEvent actionEvent) {
        Row curGroup =
            ADFUtils.findIterator("DmsEnabledRoleIterator").getCurrentRow();
        if (curGroup != null) {
            ViewObject vo =
                ADFUtils.findIterator("DmsUnassignedFunctionViewIterator").getViewObject();
            vo.setNamedWhereClauseParam("roleId", curGroup.getAttribute("Id"));
            vo.executeQuery();
            RichPopup.PopupHints hint = new RichPopup.PopupHints();
            this.popup.show(hint);
        }
    }

    public void removeFunction(ActionEvent actionEvent) {

        if (this.assignedFunctionTable.getSelectedRowKeys() != null) {
            RowKeySet rowKeys =
                this.assignedFunctionTable.getSelectedRowKeys();
            Object[] rowKeySetArray = rowKeys.toArray();
            CollectionModel cm =
                (CollectionModel)assignedFunctionTable.getValue();

            for (Object key : rowKeySetArray) {
                assignedFunctionTable.setRowKey(key);
                JUCtrlHierNodeBinding rowData =
                    (JUCtrlHierNodeBinding)cm.getRowData();
                if (rowData == null)
                    return ;
                
                rowData.getRow().remove();
            }
            ADFUtils.findIterator("DmsRoleFunctionViewIterator").getViewObject().getApplicationModule().getTransaction().commit();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.assignedFunctionTable);
        }
    }

    public void setPopup(RichPopup popup) {
        this.popup = popup;
    }

    public RichPopup getPopup() {
        return popup;
    }

    public void add_function(ActionEvent actionEvent) {
        if (this.unassignedFunctionTable.getSelectedRowKeys() != null) {
            ViewObject roleFunctionVo =
                ADFUtils.findIterator("DmsRoleFunctionViewIterator").getViewObject();
            String roleId =
                (String)ADFUtils.findIterator("DmsEnabledRoleIterator").getViewObject().getCurrentRow().getAttribute("Id");
            Iterator itr =
                this.unassignedFunctionTable.getSelectedRowKeys().iterator();
            RowSetIterator rowSetIterator =
                ADFUtils.findIterator("DmsUnassignedFunctionViewIterator").getRowSetIterator();
            while (itr.hasNext()) {
                List key = (List)itr.next();
                Row functionRow = rowSetIterator.getRow((Key)key.get(0));
                if (functionRow == null)
                    return;

                Row row = roleFunctionVo.createRow();
                row.setAttribute("RoleId", roleId);
                row.setAttribute("FunctionId", functionRow.getAttribute("Id"));
                roleFunctionVo.insertRow(row);
            }
            roleFunctionVo.getApplicationModule().getTransaction().commit();
            ADFUtils.findIterator("DmsRoleFunctionViewIterator").getViewObject().executeQuery();
            ADFUtils.findIterator("DmsUnassignedFunctionViewIterator").getViewObject().executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.assignedFunctionTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.unassignedFunctionTable);
        }
    }

    public void setUnassignedFunctionTable(RichTable unassignedFunctionTable) {
        this.unassignedFunctionTable = unassignedFunctionTable;
    }

    public RichTable getUnassignedFunctionTable() {
        return unassignedFunctionTable;
    }
}

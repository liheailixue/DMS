package dcm;

import common.ADFUtils;
import common.DmsUtils;

import dms.login.Person;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

import java.sql.Types;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaItem;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

import oracle.jbo.server.DBTransaction;

import org.apache.commons.lang.ObjectUtils;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.UploadedFile;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.hexj.excelhandler.reader.ExcelReaderUtil;
import org.hexj.excelhandler.writer.AbstractExcel2007Writer;

public class DcmDataDisplayBean extends AbstractExcel2007Writer {
    private CollectionModel dataModel;
    private String curTemplateId;
    private String templateName;
    //模版是否只读
    private String temlateReadOnly;
    //模版对应后台表
    private String templateSrcTable;
    //模版对应视图
    private String templateSrcView;
    //模版对应零时表
    private String templateTmpTable;
    //模版预处理程序（执行于校验程序前）
    private String templatePreProgram;
    //模版处理程序（将数据从临时表导入到真实表）
    private String templateProgram;
    //模版善后程序（执行于数据导入到真实表后）
    private String templateAfterProgram;
    //模版处理模式（覆盖式导入、增量导入）
    private String templateMode;
    //模版定义文件
    private String templateFile;
    //数据起始行号
    private int dataStartLine;
    //模版对应组合ID
    private String combinationId;
    //模版列定义
    private List<ColumnDef> colsdef = new ArrayList<ColumnDef>();
    private boolean isIncrement = true;
    private boolean isXlsx = true;
    private List<ComHeader> templateHeader = new ArrayList<ComHeader>();
    private static ADFLogger _logger =
        ADFLogger.createADFLogger(DcmDataDisplayBean.class);
    private RichInputFile fileInput;
    private Map headerComponents = new LinkedHashMap();
    private RichPanelCollection panelaCollection;
    private Person curUser;

    public DcmDataDisplayBean() {
        this.curUser =
                (Person)ADFContext.getCurrent().getSessionScope().get("cur_user");
        this.dataModel = new DcmDataTableModel();
        this.initTemplateData();
        this.initColsDef();
        this.initCombination();
        this.initModelData();
    }

    public CollectionModel getDataModel() {
        return this.dataModel;
    }

    public void valueChangeListener(ValueChangeEvent valueChangeEvent) {
        Map rowData = (Map)this.dataModel.getRowData();
        if (((DcmDataTableModel)this.dataModel).getSelectedRows().size() > 1) {
            String msg =
                DmsUtils.getMsg("dcm.msg.can_not_select_multiple_row");
            FacesMessage fm = new FacesMessage("", msg);
            FacesContext.getCurrentInstance().addMessage(null, fm);
            return;
        }
        if (null == rowData.get("OPERATION")) {
            rowData.put("OPERATION", DcmDataTableModel.OPERATE_UPDATE);
        }
    }

    public void rowSelectionListener(SelectionEvent selectionEvent) {
        RichTable table = (RichTable)selectionEvent.getSource();
        RowKeySet rks = selectionEvent.getAddedSet();
        if (rks != null) {
            int setSize = rks.size();
            if (setSize == 0) {
                return;
            }
            Object rowKey = rks.iterator().next();
            table.setRowKey(rowKey);
        }
    }

    public void operation_new() {
        List<Map> modelData = (List<Map>)this.dataModel.getWrappedData();
        Map newRow = new HashMap();
        for (ColumnDef col : this.colsdef) {
            newRow.put(col.getCode(), null);
        }
        newRow.put("OPERATION", DcmDataTableModel.OPERATE_CREATE);
        modelData.add(0, newRow);
    }

    public void operation_delete() {
        List<Map> modelData = (List<Map>)this.dataModel.getWrappedData();
        RowKeySet keySet =
            ((DcmDataTableModel)this.dataModel).getSelectedRows();
        for (Object key : keySet) {
            Map rowData = (Map)this.dataModel.getRowData(key);
            if (DcmDataTableModel.OPERATE_CREATE.equals(rowData.get("OPERATION"))) {
                modelData.remove(rowData);
            } else if (DcmDataTableModel.OPERATE_UPDATE.equals(rowData.get("OPERATION")) ||
                       null == rowData.get("OPERATION")) {
                rowData.put("OPERATION", DcmDataTableModel.OPERATE_DELETE);
            }
        }
    }

    public void operation_save() {
        String curComRecordId = this.getCurCombinationRecord();
        List<Map> modelData = (List<Map>)this.dataModel.getWrappedData();
        StringBuffer sql_insert = new StringBuffer();
        StringBuffer sql_value = new StringBuffer();
        sql_insert.append("INSERT INTO \"").append(this.templateTmpTable).append("\"(");
        sql_insert.append("TEMPLATE_ID,COM_RECORD_ID,ORIGIN_ROWID");
        sql_insert.append(",CREATED_BY,UPDATED_BY,CREATED_AT,UPDATED_AT,EDIT_TYPE");
        sql_value.append(" VALUES('").append(this.curTemplateId).append("'");
        sql_value.append(",'").append(ObjectUtils.toString(curComRecordId)).append("'");
        sql_value.append(",?,'").append(this.curUser.getId()).append("'");
        sql_value.append(",'").append(this.curUser.getId()).append("'");
        sql_value.append(",SYSDATE,SYSDATE,?");
        for (int i = 0; i < this.colsdef.size(); i++) {
            sql_insert.append(",COLUMN").append(i + 1);
            sql_value.append(",?");
        }
        sql_insert.append(")");
        sql_value.append(")");
        try {
            DBTransaction trans =
                (DBTransaction)ADFUtils.findIterator("DcmTemplateViewIterator").getViewObject().getApplicationModule().getTransaction();
            trans.createStatement(0).execute("DELETE FROM \""+this.templateTmpTable
                                             +"\" WHERE TEMPLATE_ID='"+this.curTemplateId+"' AND COM_RECORD_ID"+
                                             (this.combinationId==null ? "IS NULL" : ("='"+curComRecordId+"'")));
            trans.commit();
            PreparedStatement stat =
                trans.createPreparedStatement(sql_insert.toString() +
                                              sql_value.toString(), 0);
            for (Map rowData : modelData) {
                if (DcmDataTableModel.OPERATE_CREATE.equals(rowData.get("OPERATION"))) {
                    stat.setString(2, "NEW");
                } else if (DcmDataTableModel.OPERATE_DELETE.equals(rowData.get("OPERATION"))) {
                    stat.setString(2, "DELETE");
                } else if (DcmDataTableModel.OPERATE_UPDATE.equals(rowData.get("OPERATION"))) {
                    stat.setString(2, "UPDATE");
                }
                if (null != rowData.get("OPERATION")) {
                    for (int i = 0; i < this.colsdef.size(); i++) {
                        stat.setString(3 + i,
                                       (String)rowData.get(this.colsdef.get(i).getCode()));
                    }
                    stat.setString(1, (String)rowData.get("ROW_ID"));
                    stat.addBatch();
                }
            }
            stat.executeBatch();
            trans.commit();
            stat.close();
            this.handleData("EDIT", curComRecordId);
            this.initModelData();
        } catch (Exception e) {
            this._logger.severe(e);
        }
    }

    public void operation_reset() {
        this.initModelData();
    }

    public void operation_import() {
        String curComRecordId = this.getCurCombinationRecord();
        if (this.combinationId != null && curComRecordId == null) {
            String msg =
                DmsUtils.getMsg("dcm.inform.select_correct_combination");
            FacesMessage fm = new FacesMessage("", msg);
            FacesContext.getCurrentInstance().addMessage(null, fm);
            return;
        }
        if (null == this.fileInput.getValue()) {
            String msg = DmsUtils.getMsg("dcm.plz_select_import_file");
            FacesMessage fm = new FacesMessage(msg);
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, fm);
            return;
        }
        String filePath = this.uploadFile();
        this.fileInput.resetValue();
        if (null == filePath) {
            return;
        }
        if (!this.handleExcel(filePath, curComRecordId)) {
            return;
        }
        if (this.handleData(this.isIncrement ? "INCREMENT" : "REPLACE",
                            curComRecordId)) {
            String msg = DmsUtils.getMsg("dcm.inform.data_import_success");
            FacesMessage fm = new FacesMessage("", msg);
            FacesContext.getCurrentInstance().addMessage(null, fm);
        } else {
            //TODO 如果导入过程发生错误则显示错误信息
        }
        this.initModelData();
        return;
    }
    //执行数据校验和数据转移

    private boolean handleData(String mode, String curComRecordId) {
        boolean successFlag = true;
        DBTransaction trans =
            (DBTransaction)ADFUtils.findIterator("DcmTemplateViewIterator").getViewObject().getApplicationModule().getTransaction();
        try {
            //执行前置程序
            if (this.templatePreProgram != null) {
                CallableStatement prcs =
                    trans.createCallableStatement("CALl " +
                                                  this.templatePreProgram +
                                                  "(?,?,?,?,?)", 0);
                prcs.setString(1, this.curTemplateId);
                prcs.setString(2, curComRecordId);
                prcs.setString(3, this.curUser.getId());
                prcs.setString(4, mode);
                prcs.setString(5, this.curUser.getLocale());
                prcs.execute();
                trans.commit();
                prcs.close();
            }
            //执行校验程序
            ViewObject vo =
                ADFUtils.findIterator("DcmValidationQueryViewIterator").getViewObject();
            vo.setNamedWhereClauseParam("templateId", this.curTemplateId);
            vo.executeQuery();
            while (vo.hasNext()) {
                Row row = vo.next();
                CallableStatement cs =
                    trans.createCallableStatement("CALl " + row.getAttribute("Program") +
                                                  "(?,?,?,?,?,?,?,?,?)", 0);
                cs.setString(1, (String)row.getAttribute("ValidationId"));
                cs.setString(2, this.curTemplateId);
                cs.setString(3, curComRecordId);
                for (int i = 1; i <= this.colsdef.size(); i++) {
                    if (this.colsdef.get(i -
                                         1).getCode().equals((String)row.getAttribute("DbTableCol"))) {
                        cs.setString(4, "COLUMN" + i);
                    }
                }
                cs.setString(5, (String)row.getAttribute("DbTableCol"));
                cs.setString(6, mode);
                cs.setString(7, this.curUser.getLocale());
                cs.setString(8, (String)row.getAttribute("Args"));
                cs.registerOutParameter(9, Types.VARCHAR);
                cs.execute();
                if ("N".equals(cs.getString(9))) {
                    successFlag = false;
                }
                ;
                trans.commit();
                cs.close();
            }
            //执行数据导入
            CallableStatement cs =
                trans.createCallableStatement("CALl " + this.templateProgram +
                                              "(?,?,?,?,?)", 0);
            cs.setString(1, this.curTemplateId);
            cs.setString(2, curComRecordId);
            cs.setString(3, this.curUser.getId());
            cs.setString(4, mode);
            cs.setString(5, this.curUser.getLocale());
            cs.execute();
            trans.commit();
            cs.close();
            //执行善后程序
            if (this.templateAfterProgram != null) {
                CallableStatement afcs =
                    trans.createCallableStatement("CALl " +
                                                  this.templateAfterProgram +
                                                  "(?,?,?,?,?)", 0);
                afcs.setString(1, this.curTemplateId);
                afcs.setString(2, curComRecordId);
                afcs.setString(3, this.curUser.getId());
                afcs.setString(4, mode);
                afcs.setString(5, this.curUser.getLocale());
                afcs.execute();
                trans.commit();
                afcs.close();
            }
        } catch (Exception e) {
            this._logger.severe(e);
        }
        return successFlag;
    }
    //获取当前的组合

    private String getCurCombinationRecord() {
        String comRecordId = null;
        if (this.combinationId != null) {
            ViewObject vo =
                ADFUtils.findIterator("DcmCombinationViewIterator").getViewObject();
            ViewCriteria vc = vo.createViewCriteria();
            ViewCriteriaRow vcr = vc.createViewCriteriaRow();
            ViewCriteriaItem item = vcr.ensureCriteriaItem("Id");
            item.setOperator("=");
            item.getValues().get(0).setValue(this.combinationId);
            vc.addRow(vcr);
            vo.applyViewCriteria(vc);
            vo.executeQuery();
            String combinationCode = null;
            if (vo.hasNext()) {
                Row row = vo.next();
                combinationCode = (String)row.getAttribute("Code");
            }
            if (null != combinationCode) {
                StringBuffer sql = new StringBuffer();
                sql.append("SELECT ID FROM ").append("\"").append(combinationCode.toUpperCase()).append("\"");
                sql.append(" WHERE 1=1");
                for (Object headerCode : headerComponents.keySet()) {
                    sql.append(" AND ");
                    sql.append("\"").append(headerCode.toString().toUpperCase()).append("\"='").append(((RichSelectOneChoice)headerComponents.get(headerCode)).getValue()).append("'");
                }
                DBTransaction trans =
                    (DBTransaction)ADFUtils.findIterator("DcmTemplateViewIterator").getViewObject().getApplicationModule().getTransaction();
                Statement stat = trans.createStatement(1);
                try {
                    ResultSet rs = stat.executeQuery(sql.toString());
                    if (rs.next()) {
                        comRecordId = rs.getString("ID");
                    }
                } catch (SQLException e) {
                    this._logger.severe(e);
                }
            }
        }
        return comRecordId;
    }
    //获取当前组合的文本信息用于拼接文件名

    private String getCurComRecordText() {
        String text = null;
        if (this.combinationId == null) {
            text = "";
        } else {
            for (ComHeader header : this.templateHeader) {
                if (header.getValue() == null) {
                    text = null;
                    break;
                } else {
                    text = (text == null ? "" : (text + "_"));
                    for (SelectItem item : header.getValues()) {
                        if (header.getValue().equals(item.getValue())) {
                            text += item.getLabel();
                        }
                    }
                }
            }
        }
        return text;
    }
    //读取excel数据到零时表

    private boolean handleExcel(String fileName, String curComRecordId) {
        DBTransaction trans =
            (DBTransaction)ADFUtils.findIterator("DcmTemplateViewIterator").getViewObject().getApplicationModule().getTransaction();
        String combinationRecord = ObjectUtils.toString(curComRecordId);
        //清空已有零时表数据
        try {
            trans.createStatement(0).execute("DELETE FROM \"" +
                                             this.templateTmpTable.toUpperCase() +
                                             "\" WHERE TEMPLATE_ID='" +
                                             this.curTemplateId +
                                             "' AND COM_RECORD_ID " +
                                             (this.combinationId == null ?
                                              "IS NULL" :
                                              ("='" + combinationRecord +
                                               "'")));
        } catch (SQLException e) {
            this._logger.severe(e);
            String msg = DmsUtils.getMsg("dcm.alter.clear_tmp_table_error");
            FacesMessage fm = new FacesMessage("", msg);
            FacesContext.getCurrentInstance().addMessage(null, fm);
            return false;
        }
        RowReader reader =
            new RowReader(trans, this.dataStartLine, this.curTemplateId,
                          combinationRecord, this.templateTmpTable,
                          this.colsdef.size(), this.curUser.getId());
        try {
            ExcelReaderUtil.readExcel(reader, fileName, true);
            reader.close();
        } catch (Exception e) {
            this._logger.severe(e);
            String msg = DmsUtils.getMsg("dcm.excel_handle_error");
            FacesMessage fm = new FacesMessage("", msg);
            FacesContext.getCurrentInstance().addMessage(null, fm);
            return false;
        }
        return true;

    }
    //文件上传

    private String uploadFile() {
        UploadedFile file = (UploadedFile)this.fileInput.getValue();
        if (!(file.getFilename().endsWith(".xls") ||
              file.getFilename().endsWith(".xlsx"))) {
            String msg = DmsUtils.getMsg("dcm.file_format_error");
            FacesMessage fm = new FacesMessage("", msg);
            FacesContext.getCurrentInstance().addMessage(null, fm);
            return null;
        }
        String fileExtension =
            file.getFilename().substring(file.getFilename().lastIndexOf("."));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = dateFormat.format(new Date());
        File dmsBaseDir = new File("DMS/UPLOAD/" + this.templateName);
        if (!dmsBaseDir.exists()) {
            dmsBaseDir.mkdirs();
        }
        String fileName = "DMS/UPLOAD/" + this.templateName + "/";
        if (this.combinationId == null) {
            fileName +=
                    this.templateName + "_" + this.curUser.getName() + "_" +
                    date + fileExtension;
        } else {
            fileName +=
                    this.getCurComRecordText() + "_" + this.curUser.getName() +
                    "_" + date + fileExtension;
        }
        try {
            InputStream inputStream = file.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(fileName);
            BufferedInputStream bufferedInputStream =
                new BufferedInputStream(inputStream);
            BufferedOutputStream bufferedOutputStream =
                new BufferedOutputStream(outputStream);
            byte[] buffer = new byte[10240];
            int bytesRead = 0;
            while ((bytesRead = bufferedInputStream.read(buffer, 0, 10240)) !=
                   -1) {
                bufferedOutputStream.write(buffer, 0, bytesRead);
            }
            bufferedOutputStream.flush();
            if (bufferedOutputStream != null) {
                bufferedOutputStream.close();
            }
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
            file.dispose();
        } catch (IOException e) {
            this._logger.severe(e);
            String msg = DmsUtils.getMsg("dcm.file_upload_error");
            FacesMessage fm = new FacesMessage("", msg);
            FacesContext.getCurrentInstance().addMessage(null, fm);
            return null;
        }
        return (new File(fileName)).getAbsolutePath();
    }
    //数据导出

    public void operation_export(FacesContext facesContext,
                                 java.io.OutputStream outputStream) {
        String type = this.isXlsx ? "xlsx" : "xls";
        try {
            if ("xls".equals(type)) {
                String querySql = this.getQuerySql();
                DBTransaction dbTransaction =
                    (DBTransaction)ADFUtils.findIterator("DcmTemplateViewIterator").getViewObject().getApplicationModule().getTransaction();
                PreparedStatement stat =
                    dbTransaction.createPreparedStatement(querySql, -1);
                ResultSet rs = stat.executeQuery();
                // 创建excel2003对象
                Workbook wb = new HSSFWorkbook();
                // 创建新的表单
                Sheet sheet = wb.createSheet(this.templateName);
                // 创建新行
                org.apache.poi.ss.usermodel.Row headerRow =
                    sheet.createRow(this.dataStartLine - 2);
                for (int i = 0; i < this.colsdef.size(); i++) {
                    headerRow.createCell(i).setCellValue(this.colsdef.get(i).getLabel());
                }
                int n = this.dataStartLine - 1;
                while (rs.next()) {
                    int colInx = 0;
                    org.apache.poi.ss.usermodel.Row row = sheet.createRow(n);
                    for (ColumnDef col : this.colsdef) {
                        Cell cell = row.createCell(colInx);
                        ++colInx;
                        cell.setCellValue(rs.getString(col.getCode().toUpperCase()));
                    }
                    ++n;
                }
                rs.close();
                wb.write(outputStream);
            } else {
                this.process(outputStream, this.templateName);
            }
            outputStream.flush();
        } catch (Exception e) {
            this._logger.severe(e);
        }
    }

    public void operation_download(FacesContext facesContext,
                                   java.io.OutputStream outputStream) {
        try {
            if (this.isXlsx) {
                if (this.templateFile == null) {
                    XSSFWorkbook wb = new XSSFWorkbook();
                    XSSFSheet sheet = wb.createSheet(this.templateName);
                    XSSFRow row = sheet.createRow(this.dataStartLine - 2);
                    for (int i = 0; i < this.colsdef.size(); i++) {
                        row.createCell(i).setCellValue(this.colsdef.get(i).getLabel());
                    }
                    wb.write(outputStream);
                    outputStream.flush();
                } else {
                    FileInputStream inputStream =
                        new FileInputStream(this.templateFile + ".xlsx");
                    byte[] buffer = new byte[10240];
                    int bytesRead = 0;
                    while ((bytesRead = inputStream.read(buffer, 0, 10240)) !=
                           -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.flush();
                }
            } else {
                if (this.templateFile == null) {
                    // 创建excel2003对象
                    Workbook wb = new HSSFWorkbook();
                    // 创建新的表单
                    Sheet sheet = wb.createSheet(this.templateName);
                    // 创建新行
                    org.apache.poi.ss.usermodel.Row headerRow =
                        sheet.createRow(this.dataStartLine - 2);
                    for (int i = 0; i < this.colsdef.size(); i++) {
                        headerRow.createCell(i).setCellValue(this.colsdef.get(i).getLabel());
                    }
                    wb.write(outputStream);
                    outputStream.flush();
                } else {
                    FileInputStream inputStream =
                        new FileInputStream(this.templateFile + ".xls");
                    byte[] buffer = new byte[10240];
                    int bytesRead = 0;
                    while ((bytesRead = inputStream.read(buffer, 0, 10240)) !=
                           -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.flush();
                }
            }
        } catch (Exception e) {
            this._logger.severe(e);
            String msg = DmsUtils.getMsg("dcm.template_download_error");
            FacesMessage fm = new FacesMessage(msg);
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, fm);
            return;
        }
    }
    //初始化模版信息

    private void initTemplateData() {
        this.curTemplateId =
                (String)ADFContext.getCurrent().getPageFlowScope().get("curTemplateId");
        ViewObject templateView =
            ADFUtils.findIterator("DcmTemplateViewIterator").getViewObject();
        ViewCriteria vc = templateView.createViewCriteria();
        ViewCriteriaRow vcr = vc.createViewCriteriaRow();
        ViewCriteriaItem item = vcr.ensureCriteriaItem("Id");
        item.setOperator("=");
        item.getValues().get(0).setValue(this.curTemplateId);
        vc.addRow(vcr);
        templateView.applyViewCriteria(vc);
        templateView.executeQuery();
        if (templateView.hasNext()) {
            Row row = templateView.next();
            this.temlateReadOnly = (String)row.getAttribute("Readonly");
            this.templateSrcTable = (String)row.getAttribute("DbTable");
            this.templateSrcView = (String)row.getAttribute("DbView");
            this.templateTmpTable = (String)row.getAttribute("TmpTable");
            this.templatePreProgram = (String)row.getAttribute("PreProgram");
            this.templateProgram = (String)row.getAttribute("HandleProgram");
            this.templateAfterProgram =
                    (String)row.getAttribute("AfterProgram");
            this.templateMode = (String)row.getAttribute("HandleMode");
            this.templateFile = (String)row.getAttribute("TemplateFile");
            this.dataStartLine =
                    ((oracle.jbo.domain.Number)row.getAttribute("DataStartLine")).intValue();
            this.combinationId = (String)row.getAttribute("CombinationId");
            this.templateName = (String)row.getAttribute("Name");
        }
    }
    //初始化模版列定义信息

    private void initColsDef() {
        ViewObject colsView =
            ADFUtils.findIterator("DcmTemplateColumnViewIterator").getViewObject();
        ViewCriteria vc = colsView.createViewCriteria();
        ViewCriteriaRow vcr = vc.createViewCriteriaRow();
        ViewCriteriaItem item = vcr.ensureCriteriaItem("TemplateId");
        item.setOperator("=");
        item.getValues().get(0).setValue(this.curTemplateId);
        vc.addRow(vcr);
        colsView.applyViewCriteria(vc);
        colsView.executeQuery();
        while (colsView.hasNext()) {
            Row row = colsView.next();
            ColumnDef colDef = new ColumnDef();
            colDef.setLabel((String)row.getAttribute("ColumnLabel"));
            colDef.setCode((String)row.getAttribute("DbTableCol"));
            colDef.setIsPk((String)row.getAttribute("IsPk"));
            colDef.setReadonly((String)row.getAttribute("Readonly"));
            colDef.setType((String)row.getAttribute("DataType"));
            colDef.setVisible((String)row.getAttribute("Visible"));
            this.colsdef.add(colDef);
        }
        ((DcmDataTableModel)this.dataModel).setColsdef(this.colsdef);
    }
    //初始化模版数据

    private void initModelData() {
        List<Map> data = new ArrayList<Map>();
        DBTransaction dbTransaction =
            (DBTransaction)ADFUtils.findIterator("DcmTemplateViewIterator").getViewObject().getApplicationModule().getTransaction();
        String sql = this.getQuerySql();
        PreparedStatement stat =
            dbTransaction.createPreparedStatement(sql, -1);
        ResultSet rs = null;
        try {
            rs = stat.executeQuery();
            while (rs.next()) {
                Map row = new HashMap();
                for (ColumnDef col : this.colsdef) {
                    row.put(col.getCode(),
                            rs.getString(col.getCode().toUpperCase()));
                }
                row.put("ROW_ID", rs.getString("ROW_ID"));
                data.add(row);
            }
            rs.close();
            this.dataModel.setWrappedData(data);
        } catch (SQLException e) {
            this._logger.severe(e);
        }
    }
    //获取数据查询语句

    private String getQuerySql() {
        StringBuffer sql_select = new StringBuffer(200);
        StringBuffer sql_from = new StringBuffer(100);
        StringBuffer sql_where = new StringBuffer(100);
        if (null != this.templateSrcView) {
            sql_select.append("SELECT ROW_ID");
            sql_from.append(" ").append("FROM ").append(this.templateSrcView);
        } else {
            sql_select.append("SELECT ROWID ROW_ID");
            sql_from.append(" ").append("FROM ").append(this.templateSrcTable);
        }
        for (ColumnDef col : this.colsdef) {
            sql_select.append(",\"").append(col.getCode().toUpperCase()).append("\"");
        }
        if (this.combinationId != null) {
            sql_where.append(" WHERE COM_RECORD_ID='").append(this.getCurCombinationRecord()).append("'");
        }
        sql_where.append(" ORDER BY IDX");
        return sql_select.toString() + sql_from.toString() +
            sql_where.toString();
    }
    //初始化组合信息

    private void initCombination() {
        if (null != this.combinationId) {
            ViewObject vo =
                ADFUtils.findIterator("DcmComVsViewIterator").getViewObject();
            vo.setNamedWhereClauseParam("combinationId", this.combinationId);
            vo.executeQuery();
            while (vo.hasNext()) {
                Row row = vo.next();
                ComHeader header = new ComHeader();
                header.setName((String)row.getAttribute("Name"));
                header.setIsAuthority((String)row.getAttribute("IsAuthority"));
                header.setSrcTable((String)row.getAttribute("Source"));
                header.setValueSetId((String)row.getAttribute("ValueSetId"));
                header.setValues(this.fetchHeaderValueList(header));
                header.setValue(header.getValues().size() > 0 ?
                                (String)header.getValues().get(0).getValue() :
                                null);
                header.setCode((String)row.getAttribute("Code"));
                this.templateHeader.add(header);
            }
        }
    }
    //获取某一值集值列表

    private List<SelectItem> fetchHeaderValueList(ComHeader header) {
        List<SelectItem> values = new ArrayList<SelectItem>();
        DBTransaction dbTransaction =
            (DBTransaction)ADFUtils.findIterator("DcmTemplateViewIterator").getViewObject().getApplicationModule().getTransaction();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT CODE,MEANING FROM ");
        sql.append(header.getSrcTable());
        sql.append(" WHERE LOCALE=? ORDER BY IDX");
        PreparedStatement stat =
            dbTransaction.createPreparedStatement(sql.toString(), -1);
        try {
            stat.setString(1,
                           (String)((Person)ADFContext.getCurrent().getSessionScope().get("cur_user")).getLocale());
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                SelectItem item = new SelectItem();
                item.setLabel(rs.getString("MEANING"));
                item.setValue(rs.getString("CODE"));
                values.add(item);
            }
        } catch (SQLException e) {
            this._logger.severe(e);
        }
        return values;
    }

    public String getCombinationId() {
        return combinationId;
    }

    public void setTemplateHeader(List<ComHeader> templateHeader) {
        this.templateHeader = templateHeader;
    }

    public List<ComHeader> getTemplateHeader() {
        return templateHeader;
    }

    public void setFileInput(RichInputFile fileInput) {
        this.fileInput = fileInput;
    }

    public RichInputFile getFileInput() {
        return fileInput;
    }
    //选择不同的组合时的处理逻辑

    public void headerSelectChangeListener(ValueChangeEvent valueChangeEvent) {
        RichSelectOneChoice header =
            (RichSelectOneChoice)valueChangeEvent.getSource();
        int flag = 0;
        for (Object key : this.headerComponents.keySet()) {
            //刷新后续表头数据
            if (flag == 1) {
                ComHeader headerData = null;
                for (ComHeader h : this.templateHeader) {
                    if (key.equals(h.getCode())) {
                        headerData = h;
                    }
                }
                headerData.setValues(this.fetchHeaderValueList(headerData));
                RichSelectOneChoice rsoc =
                    (RichSelectOneChoice)this.headerComponents.get(key);
                SelectItem item =
                    headerData.getValues().size() > 0 ? headerData.getValues().get(0) :
                    null;
                //设置默认值
                headerData.setValue((String)item.getValue());
                AdfFacesContext adfFacesContext =
                    AdfFacesContext.getCurrentInstance();
                adfFacesContext.addPartialTarget(rsoc);
            }
            if (header.equals(this.headerComponents.get(key))) {
                flag = 1;
            }
        }
        this.initModelData();
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        adfFacesContext.addPartialTarget(this.panelaCollection);
    }

    public Map getHeaderComponents() {
        return headerComponents;
    }

    public void setPanelaCollection(RichPanelCollection panelaCollection) {
        this.panelaCollection = panelaCollection;
    }

    public RichPanelCollection getPanelaCollection() {
        return panelaCollection;
    }

    public void generate() {
        try {
            String querySql = this.getQuerySql();
            DBTransaction dbTransaction =
                (DBTransaction)ADFUtils.findIterator("DcmTemplateViewIterator").getViewObject().getApplicationModule().getTransaction();
            PreparedStatement stat =
                dbTransaction.createPreparedStatement(querySql, -1);
            ResultSet rs = stat.executeQuery();
            //电子表格开始
            beginSheet();
            insertRow(this.dataStartLine - 2);
            for (int i = 0; i < this.colsdef.size(); i++) {
                createCell(i, this.colsdef.get(i).getLabel());
            }
            endRow();
            int n = this.dataStartLine - 1;
            while (rs.next()) {
                int colInx = 0;
                insertRow(n);
                for (ColumnDef col : this.colsdef) {
                    createCell(colInx,
                               rs.getString(col.getCode().toUpperCase()));
                    ++colInx;
                }
                ++n;
                //结束行
                endRow();
            }
            rs.close();
            //电子表格结束
            endSheet();
        } catch (Exception e) {
            this._logger.severe(e);
        }
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setIsIncrement(boolean isIncrement) {
        this.isIncrement = isIncrement;
    }

    public boolean isIsIncrement() {
        return isIncrement;
    }

    public void setIsXlsx(boolean isXlsx) {
        this.isXlsx = isXlsx;
    }

    public boolean isIsXlsx() {
        return isXlsx;
    }

    public String getExportDataExcelName() {
        if (this.isXlsx) {
            return this.templateName + this.getCurCombinationRecord() +
                ".xlsx";
        } else {
            return this.templateName + this.getCurCombinationRecord() + ".xls";
        }
    }

    public String getExportTemplateExcelName() {
        if (this.isXlsx) {
            return this.templateName + ".xlsx";
        } else {
            return this.templateName + ".xls";
        }
    }
}
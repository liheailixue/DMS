package dgpt;

import common.ADFUtils;
import common.DmsLog;
import common.DmsUtils;

import common.JSFUtils;

import common.lov.DmsComBoxLov;

import common.lov.ValueSetRow;

import dcm.DcmDataDisplayBean;
import dcm.DcmDataTableModel;
import dcm.PcColumnDef;

import dcm.PcDataTableModel;

import dcm.PcExcel2003WriterImpl;

import dcm.PcExcel2007WriterImpl;

import dcm.SPRowReader;

import dms.login.Person;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Types;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import java.util.Map;

import java.util.UUID;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;

import oracle.jbo.ViewObject;
import oracle.jbo.jbotester.load.SimpleDateFormatter;
import oracle.jbo.server.DBTransaction;

import org.apache.commons.lang.ObjectUtils;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.UploadedFile;

import org.hexj.excelhandler.reader.ExcelReaderUtil;

import utils.system;

public class HtChangeBean {
    private Person curUser;
    //页面绑定组件
    private RichPanelCollection panelaCollection;
    private RichPopup errorWindow;
    private RichInputFile fileInput;
    private RichPopup dataImportWnd;
    private RichPopup dataExportWnd;

    private CollectionModel dataModel;
    private List<PcColumnDef> pcColsDef = new ArrayList<PcColumnDef>();
    private List<PcColumnDef> pcColsEx = new ArrayList<PcColumnDef>();
    
    private String year;
    private List<SelectItem> yearList;
    private String entity;
    private String pLine;
    private String pname;
    private List<SelectItem> pnameList;
    private DmsComBoxLov proLov;
    private String version;
    private List<SelectItem> versionList;
    private String proType;
    private String hLine;
    private String yLine;
    private String pStart;
    private String pEnd;
    private String connectId;
    private String newVersion;
    private String newEnd;
    
    DmsLog dmsLog = new DmsLog();
    public static final String TYPE_CHANGE="CHANGE";
    //日志
    private static ADFLogger _logger =ADFLogger.createADFLogger(DcmDataDisplayBean.class);
    //是否是2007及以上格式
    private boolean isXlsx = true;
    private boolean isSelected;
    private boolean isEdited;
    private boolean isChange;
    private boolean isManager;
    private String blockedId;
    private String data_type;
    
    public HtChangeBean() {
        super();
        this.curUser = (Person)(ADFContext.getCurrent().getSessionScope().get("cur_user"));
        if("10000".equals(this.curUser.getId())){
            isManager = true;
        }else{
            isManager = false;
        }
        this.dataModel = new PcDataTableModel();
        List<Map> d = new ArrayList<Map>();
        this.dataModel.setWrappedData(d);
        isSelected = true;
        isEdited = true;
        isChange = true;
        this.initList();
    }
    
    
    private void initList(){
        this.yearList = queryYears("HLS_YEAR_C");
        this.pnameList = queryValues("PRO_PLAN_COST_HEADER","PROJECT_NAME");
        this.initProLov(pnameList);
        this.versionList = queryValues1("PRO_PLAN_COST_HEADER","VERSION");
    }
    
    private void initProLov(List<SelectItem> pnameList){
        List<ValueSetRow> vsl = new ArrayList<ValueSetRow>();
        for(SelectItem sim : pnameList){
            ValueSetRow vsr = new ValueSetRow(sim.getLabel(),sim.getLabel(),sim.getLabel());
            vsl.add(vsr);
        }
        this.proLov = new DmsComBoxLov(vsl);
    }

    //年份下拉列表
    private List<SelectItem> queryYears(String source){
        DBTransaction trans = (DBTransaction)DmsUtils.getDmsApplicationModule().getTransaction();
        Statement stat = trans.createStatement(DBTransaction.DEFAULT);
        String sql = "SELECT CODE,MEANING FROM "+ source;
        List<SelectItem> values = new ArrayList<SelectItem>();
        ResultSet rs;
        try {
            rs = stat.executeQuery(sql);
            while(rs.next()){
                SelectItem sim = new SelectItem(rs.getString("CODE"),rs.getString("MEANING"));
                values.add(sim);
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return values;
    }
    
    //其他下拉列表
    private List<SelectItem> queryValues(String source,String col){
        DBTransaction trans = (DBTransaction)DmsUtils.getDmsApplicationModule().getTransaction();
        Statement stat = trans.createStatement(DBTransaction.DEFAULT);
        String sql = "";
        if(this.curUser.getId().equals("10000")){
            sql = "SELECT DISTINCT P."+col+" FROM "+source+" P WHERE " + 
                " P.DATA_TYPE='BASE' OR P.DATA_TYPE='CHANGE'";
        }else{
            sql = "SELECT DISTINCT P."+col+" FROM "+source+" P WHERE P.PROJECT_NAME IN (" + 
            "SELECT T.PRO_CODE||'-'||T.PRO_DESC FROM SAP_DMS_PROJECT_PRIVILEGE_V T WHERE ID = '"+this.curUser.getId()+"'"+
//            "       SELECT T.PRO_CODE||'-'||T.PRO_DESC FROM SAP_DMS_PROJECT_Privilege T " +
//                "WHERE T.ATTRIBUTE3 = \'BASE\' " + 
//                "AND (T.PRO_MANAGER = '"+this.curUser.getAcc()+"' OR T.PRO_DIRECTOR='"+this.curUser.getAcc()+"')" + 
//            "UNION " +
//            "   SELECT T1.PRO_CODE||'-'||T1.PRO_DESC FROM SAP_DMS_PROJECT_Privilege T1,DMS_USER_GROUP P " +
//            "WHERE T1.ATTRIBUTE3 = 'BASE'"+
//            "AND P.GROUP_ID IN (SELECT GROUP_ID FROM DMS_USER_GROUP WHERE USER_ID='"+this.curUser.getId()+"')"+
//            "AND (T1.ATTRIBUTE6=P.GROUP_ID OR T1.ATTRIBUTE5=P.GROUP_ID)" +
            ") AND (P.DATA_TYPE ='BASE' OR P.DATA_TYPE='CHANGE')";
        }
        List<SelectItem> values = new ArrayList<SelectItem>();
        ResultSet rs;
        try {
            rs = stat.executeQuery(sql);
            while(rs.next()){
                SelectItem sim = new SelectItem(rs.getString(col),rs.getString(col));
                values.add(sim);
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return values;
    }
    
    //版本下拉列表
    private List<SelectItem> queryValues1(String source,String col){
        DBTransaction trans = (DBTransaction)DmsUtils.getDmsApplicationModule().getTransaction();
        Statement stat = trans.createStatement(DBTransaction.DEFAULT);
        String sql = "SELECT DISTINCT "+col+",VERSION_NAME FROM "+source+" WHERE DATA_TYPE ='BASE' OR DATA_TYPE = 'CHANGE'";
        
        List<SelectItem> values = new ArrayList<SelectItem>();
        ResultSet rs;
        try {
            rs = stat.executeQuery(sql);
            while(rs.next()){
                SelectItem sim = new SelectItem(rs.getString(col),rs.getString(col)+"-"+rs.getString("VERSION_NAME"));
                values.add(sim);
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return values;
    }
    
    //项目名称下拉框change
    public void projectChange(ValueChangeEvent valueChangeEvent) {
        pname =(String) valueChangeEvent.getNewValue();
        if(year!=null&&pname!=null){
            DBTransaction trans = (DBTransaction)DmsUtils.getDmsApplicationModule().getTransaction();
            Statement stat = trans.createStatement(DBTransaction.DEFAULT);
            String sql = "SELECT DISTINCT VERSION,VERSION_NAME FROM PRO_PLAN_COST_HEADER WHERE "+
                " PROJECT_NAME='"+pname+"' AND HLS_YEAR = '"+this.year+"'" +
                " AND (DATA_TYPE ='BASE' OR DATA_TYPE='CHANGE')";
            List<SelectItem> values = new ArrayList<SelectItem>();
            ResultSet rs;
            try {
                rs = stat.executeQuery(sql);
                while(rs.next()){
                    SelectItem sim = new SelectItem(rs.getString("VERSION"),rs.getString("VERSION")+"-"+rs.getString("VERSION_NAME"));
                    values.add(sim);
                }
                rs.close();
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.versionList = values;
        }
        if(year==null||version==null||pname==null){
                return;
        }else{
            isSelected = false;
            this.queryData();
            if("CHANGE".equals(data_type)){
                this.isEdited = false;
                this.isChange = false;
            }else{
                this.isEdited = true;
                this.isChange = false;
            }
            this.createTableModel(pStart, pEnd);
        }
    }
    
    //版本下拉框change
    public void versionChange(ValueChangeEvent valueChangeEvent) {
        version =(String) valueChangeEvent.getNewValue();
        if(year==null||version==null||pname==null){
            return;
        }else{
            isSelected = false;
            this.queryData();
            if("CHANGE".equals(data_type)){
                this.isEdited = false;
                this.isChange = false;
            }else{
                this.isEdited = true;
                this.isChange = true;
            }
            this.createTableModel(pStart, pEnd);
        }
    }
    
    //年下拉框change
    public void yearChange(ValueChangeEvent valueChangeEvent) {
        year = (String)valueChangeEvent.getNewValue();
        if(year!=null&&pname!=null){
            DBTransaction trans = (DBTransaction)DmsUtils.getDmsApplicationModule().getTransaction();
            Statement stat = trans.createStatement(DBTransaction.DEFAULT);
            String sql = "SELECT DISTINCT VERSION,VERSION_NAME FROM PRO_PLAN_COST_HEADER WHERE "+
                " PROJECT_NAME='"+pname+"' AND HLS_YEAR = '"+this.year+"'" +
                " AND (DATA_TYPE ='BASE' OR DATA_TYPE='CHANGE')";
            List<SelectItem> values = new ArrayList<SelectItem>();
            ResultSet rs;
            try {
                rs = stat.executeQuery(sql);
                while(rs.next()){
                    SelectItem sim = new SelectItem(rs.getString("VERSION"),rs.getString("VERSION")+"-"+rs.getString("VERSION_NAME"));
                    values.add(sim);
                }
                rs.close();
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.versionList = values;
        }
        if(year==null||version==null||pname==null){
            return;
        }else{
            isSelected = false;
            this.queryData();
            if("CHANGE".equals(data_type)){
                this.isEdited = false;
                this.isChange = false;
            }else{
                this.isEdited = true;
                this.isChange = true;
            }
            this.createTableModel(pStart, pEnd);
        }
    }
    
    //数据查询--头
     public void queryData(){
         DBTransaction trans = (DBTransaction)DmsUtils.getDmsApplicationModule().getTransaction();
         Statement stat = trans.createStatement(DBTransaction.DEFAULT);
         String sql = "SELECT ENTITY_NAME,PRODUCT_LINE,PROJECT_TYPE,INDUSTRY_LINE,BUSINESS_LINE,CONNECT_ID,PROJECT_START,PROJECT_END,DATA_TYPE"
                     + " FROM PRO_PLAN_COST_HEADER WHERE VERSION = \'"+version+"\'";
         sql = sql +" AND HLS_YEAR=\'"+year+"\'";
         sql = sql + " AND PROJECT_NAME=\'"+pname+"\'";
         sql = sql + " AND (DATA_TYPE='BASE' OR DATA_TYPE = 'CHANGE')";
         ResultSet rs;
         try {
             rs = stat.executeQuery(sql);
             entity="";
             pLine="";
             proType="";
             hLine="";
             yLine="";
             connectId="";
             while(rs.next()){
                 entity = rs.getString("ENTITY_NAME");
                 pLine = rs.getString("PRODUCT_LINE");
                 proType = rs.getString("PROJECT_TYPE");
                 hLine = rs.getString("INDUSTRY_LINE");
                 yLine = rs.getString("BUSINESS_LINE");
                 connectId=rs.getString("CONNECT_ID");
                 pStart = rs.getString("PROJECT_START");
                 pEnd = rs.getString("PROJECT_END");
                 newEnd = pEnd;
                data_type = rs.getString("DATA_TYPE");
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
    
    public void createTableModel(String startTime,String endTime){
        //行的Map
        LinkedHashMap<String,String> labelMap = getLabelMap(startTime,endTime);
        //
        DBTransaction trans = (DBTransaction)DmsUtils.getDmsApplicationModule().getTransaction();
        Statement stat = trans.createStatement(DBTransaction.DEFAULT);
        String sql = this.querySql(labelMap);
        List<Map> data = new ArrayList<Map>();
        ResultSet rs;
        try {
            rs = stat.executeQuery(sql.toString());
            while(rs.next()){
                Map row = new HashMap();
                for(Map.Entry<String,String> entry:labelMap.entrySet()){
                    if(entry.getValue().equals("PLAN_COST") || entry.getValue().startsWith("Y")){
                        row.put(entry.getValue(), this.getPrettyNumber(rs.getString(entry.getValue())));
                    }else{
                        row.put(entry.getValue(), rs.getString(entry.getValue()));
                    }
                }
                row.put("ROW_ID", rs.getString("ROW_ID"));
                row.put("DATA_TYPE", rs.getString("DATA_TYPE"));
                row.put("ACC_CODE",rs.getString("ACC_CODE"));
                row.put("CENTER_CODE",rs.getString("CENTER_CODE"));
                row.put("LGF_NUM", rs.getString("LGF_NUM"));
                row.put("LGF_TYPE", rs.getString("LGF_TYPE"));
                row.put("PLAN_QUANTITY", rs.getString("PLAN_QUANTITY"));
                row.put("PLAN_AMOUNT", rs.getString("PLAN_AMOUNT"));
                row.put("OCCURRED_QUANTITY", rs.getString("OCCURRED_QUANTITY"));
                row.put("OCCURRED_AMOUNT", rs.getString("OCCURRED_AMOUNT"));
                row.put("OCCURRED", rs.getString("OCCURRED"));
                data.add(row);
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.dataModel.setWrappedData(data);
        ((PcDataTableModel)this.dataModel).setLabelMap(labelMap);
    }
    //调整数字显示格式
    public static String getPrettyNumber(String number) {  
        if(number == null) return "";
        if(number.equals("0.0")){
            number = "";    
        }
        if(number.startsWith(".")){
            number = "0" + number;    
        }
        while(number.contains(".")&&number.endsWith("0")){
            number = number.substring(0,number.length()-2);    
        }
        return number;  
    }
    
    //查询语句
    private String querySql(LinkedHashMap<String,String> labelMap){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        for(Map.Entry<String,String> entry : labelMap.entrySet()){
            sql.append(entry.getValue()).append(",");
        }
        sql.append("ROWID AS ROW_ID,ACC_CODE,CENTER_CODE,LGF_NUM,LGF_TYPE,PLAN_QUANTITY,PLAN_AMOUNT," + 
        "OCCURRED_QUANTITY,OCCURRED_AMOUNT,OCCURRED,DATA_TYPE FROM PRO_PLAN_COST_BODY WHERE CONNECT_ID = '").append(connectId).append("'")
//        sql.append(" AND (DATA_TYPE = '").append(this.TYPE_CHANGE).append("' OR DATA_TYPE = 'BASE')")
            .append(" ORDER BY DATA_TYPE,WBS,NETWORK,TO_NUMBER(WORK_CODE)");
        return sql.toString();
    }
    //选中行，修改
    public void valueChangeLinstener(ValueChangeEvent valueChangeEvent) {
        Map rowMap = (Map)this.dataModel.getRowData();
        if (((PcDataTableModel)this.dataModel).getSelectedRows().size() > 1) {
            String msg =DmsUtils.getMsg("dcm.msg.can_not_select_multiple_row");
            JSFUtils.addFacesInformationMessage(msg);
            return;
        }
        if(rowMap.get("OPERATION") == null){
            rowMap.put("OPERATION", PcDataTableModel.OPERATE_UPDATE);    
        }                
    }
    
    //取消
    public void operation_reset(ActionEvent actionEvent) {
        DmsUtils.getDmsApplicationModule().getTransaction().rollback();
        this.queryData();
        this.createTableModel(pStart,pEnd);
    }
    
    public void closeVersion(String connectId){
        String sql = "UPDATE PRO_PLAN_COST_HEADER SET (IS_BLOCK) = 'true' WHERE CONNECT_ID = \'"+connectId+"'";
        DBTransaction trans = (DBTransaction)DmsUtils.getDmsApplicationModule().getTransaction();
        Statement stat = trans.createStatement(DBTransaction.DEFAULT);
        int flag =-1;
        try {
            flag = stat.executeUpdate(sql);
            trans.commit();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //生产新关联id,复制数据到新版本
    public void getNewVE(ActionEvent actionEvent) {
        //判断时间格式
        if(!newEnd.contains("_")){
            JSFUtils.addFacesInformationMessage("日期格式是：YYYY_MM");
            return;
        }
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy_MM");
        Date newEndDate;
        Date pEndDate;
        //生成id
        try {
            newEndDate = sdf.parse(newEnd);
            pEndDate = sdf.parse(pEnd);
            if(pEndDate.after(newEndDate)){
                JSFUtils.addFacesInformationMessage("填写日期应在"+pEnd+"之后");
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String s = UUID.randomUUID().toString();
        //去除分隔符-
        String newConnectId = s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
        //插入头表
        DBTransaction trans = (DBTransaction)DmsUtils.getDmsApplicationModule().getTransaction();
        Statement stat1 = trans.createStatement(DBTransaction.DEFAULT);
        String sql1 = "SELECT VERSION,CONNECT_ID FROM PRO_PLAN_COST_HEADER WHERE PROJECT_NAME='"+this.pname+"' AND HLS_YEAR='"+this.year+"'"+
            " AND (DATA_TYPE='BASE' OR DATA_TYPE = 'CHANGE') ORDER BY VERSION DESC ";
        String versionCode = "";
        ResultSet rs;
        try {
            rs = stat1.executeQuery(sql1);
            rs.next();
            versionCode = rs.getString("VERSION");
            blockedId = rs.getString("CONNECT_ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //构造新的版本
        char firstChar = versionCode.charAt(0);
        int number = Integer.parseInt(versionCode.substring(1))+1;
        String newCode = number+"";
        String newVeCode = firstChar+newCode;
        Statement stat = trans.createStatement(DBTransaction.DEFAULT);
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO PRO_PLAN_COST_HEADER (HLS_YEAR,ENTITY_NAME,PRODUCT_LINE,PROJECT_NAME,VERSION_NAME,VERSION,PROJECT_TYPE,INDUSTRY_LINE,")
            .append("BUSINESS_LINE,PROJECT_START,PROJECT_END,CONNECT_ID,DATA_TYPE) ");
        sql.append("VALUES('").append(year+"','").append(entity+"','").append(pLine+"','").append(pname+"','").append(newVersion+"','").append(newVeCode+"','").append(proType+"','")
            .append(hLine+"','").append(yLine+"','").append(pStart+"','").append(newEnd+"','").append(newConnectId).append("','CHANGE')");
        try {
            stat.execute(sql.toString());
            trans.commit();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //复制，type--》connectId
        LinkedHashMap<String,String> labelMap = getLabelMap(pStart,pEnd);
        StringBuffer sqlInsert = new StringBuffer();
        StringBuffer sqlValue = new StringBuffer();
        sqlInsert.append("INSERT INTO PRO_PLAN_COST_BODY (");
        sqlValue.append("SELECT ");
        for(Map.Entry<String,String> entry:labelMap.entrySet()){
            sqlInsert.append(entry.getValue()+",");
            sqlValue.append("T."+entry.getValue()+",");
        }
        sqlInsert.append("DATA_TYPE,CONNECT_ID,ACC_CODE,CENTER_CODE,LGF_NUM,LGF_TYPE,PLAN_QUANTITY,PLAN_AMOUNT,OCCURRED_QUANTITY,OCCURRED_AMOUNT,OCCURRED)");
        sqlValue.append("'BASE','").append(newConnectId).append("\',ACC_CODE,CENTER_CODE,")
                .append("LGF_NUM,LGF_TYPE,PLAN_QUANTITY,PLAN_AMOUNT,OCCURRED_QUANTITY,OCCURRED_AMOUNT,OCCURRED ")
            .append("FROM PRO_PLAN_COST_BODY T WHERE T.CONNECT_ID=\'"+connectId+"\'");
        //显示新版本，重新构造列
        try {
            trans.createStatement(DBTransaction.DEFAULT).execute(sqlInsert.toString()+sqlValue.toString());
            trans.commit();
            trans.createStatement(DBTransaction.DEFAULT).close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        //冻结基准计划成本版本
        this.closeVersion(blockedId);
//        versionList.add(new SelectItem(newVersion,newVersion));
        version = newVersion;
        versionList.add(new SelectItem(newVeCode,newVeCode+"-"+newVersion));
        version = newVeCode;
        pEnd = newEnd;
        connectId = newConnectId;
        
        this.isEdited = false;
        this.isChange = false;
        this.createTableModel(pStart,pEnd);
    }

    //新增
    public void operation_new() {
        List<Map> modelData = (List<Map>) this.dataModel.getWrappedData();
        Map newRow = new HashMap();
        for(PcColumnDef col : this.pcColsDef){
            newRow.put(col.getDbTableCol(), null);
        }
        newRow.put("OPERATION",PcDataTableModel.OPERATE_CREATE);
        modelData.add(0, newRow);
    }
    //保存并更改
    public void operation_save() {
        //清楚临时表，错误表数据
        this.deleteTempAndError();
        DBTransaction trans = (DBTransaction)DmsUtils.getDmsApplicationModule().getTransaction();
        StringBuffer sql = new StringBuffer();
        StringBuffer sql_value = new StringBuffer();
        sql_value.append(" VALUES(");
        sql.append("INSERT INTO PRO_PLAN_COST_BODY_TEMP(") ;
        //构建sql语句
        LinkedHashMap<String,String> map = this.getLabelMap(pStart,pEnd);
        int last = map.size()+1;
        for(Map.Entry<String,String> entry : map.entrySet()){
            sql.append(entry.getValue()+",");
            sql_value.append("?,");
        }
        sql.append("ROW_ID,CONNECT_ID,CREATED_BY,OPERATION,DATA_TYPE,ROW_NO,");
        sql.append("LGF_NUM,LGF_TYPE,PLAN_QUANTITY,PLAN_AMOUNT,OCCURRED_QUANTITY,OCCURRED_AMOUNT,OCCURRED,ACC_CODE,CENTER_CODE)");
        sql_value.append("?,\'"+connectId+"\',"+this.curUser.getId()+",?,\'"+this.TYPE_CHANGE+"\',?,");
        sql_value.append("?,?,?,?,?,?,?,?,?)");
        PreparedStatement stmt = trans.createPreparedStatement(sql.toString()+sql_value.toString(), 0);
        //获取数据
        int rowNum = 1;
        List<Map> modelData = (List<Map>)this.dataModel.getWrappedData();
        for(Map<String,String> rowdata : modelData){
                try {
                    stmt.setString(last, rowdata.get("ROW_ID"));
                    stmt.setInt(last+2,rowNum);
                    stmt.setString(last+3, rowdata.get("LGF_NUM"));
                    stmt.setString(last+4, rowdata.get("LGF_TYPE"));
                    stmt.setString(last+5, rowdata.get("PLAN_QUANTITY"));
                    stmt.setString(last+6, rowdata.get("PLAN_AMOUNT"));
                    stmt.setString(last+7, rowdata.get("OCCURRED_QUANTITY"));
                    stmt.setString(last+8, rowdata.get("OCCURRED_AMOUNT"));
                    stmt.setString(last+9, rowdata.get("OCCURRED"));
                    stmt.setString(last+10, rowdata.get("ACC_CODE"));
                    stmt.setString(last+11, rowdata.get("CENTER_CODE"));
                    rowNum++;
                    if(PcDataTableModel.OPERATE_UPDATE.equals(rowdata.get("OPERATION"))){
                        stmt.setString(last+1,PcDataTableModel.OPERATE_UPDATE);
                    }else if(PcDataTableModel.OPERATE_CREATE.equals(rowdata.get("OPERATION"))){
                        stmt.setString(last+1,PcDataTableModel.OPERATE_CREATE);
                    }else if(PcDataTableModel.OPERATE_DELETE.equals(rowdata.get("OPERATION"))){
                        stmt.setString(last+1,PcDataTableModel.OPERATE_DELETE);
                    }else{
                        stmt.setString(last+1,null);
                    }
                    //if(null!=rowdata.get("OPERATION")){
                        int i =1;
                        for(Map.Entry<String,String> entry : map.entrySet()){
                            stmt.setString(i++ , rowdata.get(entry.getValue()));
                        }
                        stmt.addBatch();
                        stmt.executeBatch();
                    //}
                } catch (SQLException e) {
                    e.printStackTrace();
                } 
        }
        trans.commit();
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //执行校验
        if(this.validation()){
            //校验成功，执行导入
            Statement statUpdate = trans.createStatement(DBTransaction.DEFAULT);
            String sqlUpdate = "UPDATE PRO_PLAN_COST_HEADER SET(DATA_TYPE) = 'BASE' WHERE CONNECT_ID = '"+this.connectId+"'";
            try {
                statUpdate.executeUpdate(sqlUpdate);
                trans.commit();
                statUpdate.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.inputPro();
            Statement statType = trans.createStatement(DBTransaction.DEFAULT);
            String sqlType = "UPDATE PRO_PLAN_COST_BODY SET(DATA_TYPE) = 'BASE' WHERE CONNECT_ID='"+this.connectId+"' " +
                "AND DATA_TYPE IS NULL";
            try {
                statType.executeUpdate(sqlType);
                trans.commit();
                statType.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dmsLog.operationLog(this.curUser.getAcc(),this.connectId,this.getCom(),"UPDATE");
            for(Map<String,String> rowdata : modelData){
                    rowdata.put("OPERATION", "NULL");
            }
            this.isEdited = true;
            this.createTableModel(pStart, pEnd);
        }else{
            this.showErrorPop();
        }
    }
    
    //保存数据，不变更数据类型
    public void operation_baocun(ActionEvent actionEvent) {
        //清除临时表，错误表数据
        this.deleteTempAndError();
        DBTransaction trans = (DBTransaction)DmsUtils.getDmsApplicationModule().getTransaction();
        StringBuffer sql = new StringBuffer();
        StringBuffer sql_value = new StringBuffer();
        sql_value.append(" VALUES(");
        sql.append("INSERT INTO PRO_PLAN_COST_BODY_TEMP(") ;
        //构建sql语句
        LinkedHashMap<String,String> map = this.getLabelMap(pStart,pEnd);
        int last = map.size()+1;
        for(Map.Entry<String,String> entry : map.entrySet()){
            sql.append(entry.getValue()+",");
            sql_value.append("?,");
        }
        sql.append("ROW_ID,CONNECT_ID,CREATED_BY,OPERATION,DATA_TYPE,ROW_NO,");
        sql.append("LGF_NUM,LGF_TYPE,PLAN_QUANTITY,PLAN_AMOUNT,OCCURRED_QUANTITY,OCCURRED_AMOUNT,OCCURRED,ACC_CODE,CENTER_CODE)");
        sql_value.append("?,\'"+connectId+"\',"+this.curUser.getId()+",?,?,?,");
        sql_value.append("?,?,?,?,?,?,?,?,?)");
        PreparedStatement stmt = trans.createPreparedStatement(sql.toString()+sql_value.toString(), 0);
        //获取数据
        int rowNum = 1;
        List<Map> modelData = (List<Map>)this.dataModel.getWrappedData();
        for(Map<String,String> rowdata : modelData){
                try {
                    stmt.setString(last, rowdata.get("ROW_ID"));
                    stmt.setString(last+2,rowdata.get("DATA_TYPE"));
                    stmt.setInt(last+3,rowNum);
                    stmt.setString(last+4, rowdata.get("LGF_NUM"));
                    stmt.setString(last+5, rowdata.get("LGF_TYPE"));
                    stmt.setString(last+6, rowdata.get("PLAN_QUANTITY"));
                    stmt.setString(last+7, rowdata.get("PLAN_AMOUNT"));
                    stmt.setString(last+8, rowdata.get("OCCURRED_QUANTITY"));
                    stmt.setString(last+9, rowdata.get("OCCURRED_AMOUNT"));
                    stmt.setString(last+10, rowdata.get("OCCURRED"));
                    stmt.setString(last+11, rowdata.get("ACC_CODE"));
                    stmt.setString(last+12, rowdata.get("CENTER_CODE"));
                    rowNum++;
                    if(PcDataTableModel.OPERATE_UPDATE.equals(rowdata.get("OPERATION"))){
                        stmt.setString(last+1,PcDataTableModel.OPERATE_UPDATE);
                    }else if(PcDataTableModel.OPERATE_CREATE.equals(rowdata.get("OPERATION"))){
                        stmt.setString(last+1,PcDataTableModel.OPERATE_CREATE);
                    }else if(PcDataTableModel.OPERATE_DELETE.equals(rowdata.get("OPERATION"))){
                        stmt.setString(last+1,PcDataTableModel.OPERATE_DELETE);
                    }else{
                        stmt.setString(last+1,null);
                    }
                    //if(null!=rowdata.get("OPERATION")){
                        int i =1;
                        for(Map.Entry<String,String> entry : map.entrySet()){
                            stmt.setString(i++ , rowdata.get(entry.getValue()));
                        }
                        stmt.addBatch();
                        stmt.executeBatch();
                    //}
                } catch (SQLException e) {
                    e.printStackTrace();
                } 
        }
        trans.commit();
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //执行校验
        if(this.validation()){
            //校验成功，执行导入
//            Statement statUpdate = trans.createStatement(DBTransaction.DEFAULT);
//            String sqlUpdate = "UPDATE PRO_PLAN_COST_HEADER SET(DATA_TYPE) = 'BASE' WHERE CONNECT_ID = '"+this.connectId+"'";
//            try {
//                statUpdate.executeUpdate(sqlUpdate);
//                trans.commit();
//                statUpdate.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
            this.inputPro();
           dmsLog.operationLog(this.curUser.getAcc(),"C_"+this.connectId,this.getCom(),"UPDATE");
            for(Map<String,String> rowdata : modelData){
                    rowdata.put("OPERATION", "NULL");
            }
            this.createTableModel(pStart, pEnd);
        }else{
            this.showErrorPop();
        }
    }
    
    //清空临时表和错误表数据
    public void deleteTempAndError(){
        DBTransaction trans = (DBTransaction)DmsUtils.getDmsApplicationModule().getTransaction();
        //清空临时表数据
        String sqldelete = "DELETE FROM PRO_PLAN_COST_BODY_TEMP T WHERE T.CREATED_BY = \'"+this.curUser.getId()+"\'";
        Statement st = trans.createStatement(DBTransaction.DEFAULT);
        try {
            st.executeUpdate(sqldelete);
            trans.commit();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //清空错误表数据
        String sqlError = "DELETE FROM PRO_PLAN_COST_ERROR T WHERE T.CREATED_BY = \'"+this.curUser.getId()+"\'";
        Statement sta = trans.createStatement(DBTransaction.DEFAULT);
        try {
            sta.executeUpdate(sqlError);
            trans.commit();
            sta.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //删除
    public void operation_delete() {
        List<Map> modelData = (List<Map>)this.dataModel.getWrappedData();
        RowKeySet keySet =
            ((PcDataTableModel)this.dataModel).getSelectedRows();
        for (Object key : keySet) {
            Map rowData = (Map)this.dataModel.getRowData(key);
            //若为新增操作则直接从数据集删除数据
            if (PcDataTableModel.OPERATE_CREATE.equals(rowData.get("OPERATION"))) {
                modelData.remove(rowData);
            } 
            //若为更新或数据未修改则直接将数据集数据标记为删除
            else if (PcDataTableModel.OPERATE_UPDATE.equals(rowData.get("OPERATION")) ||
                       null == rowData.get("OPERATION")) {
                rowData.put("OPERATION", PcDataTableModel.OPERATE_DELETE);
            }
            //已经为删除状态的数据无需做任何处理
        }
    }
    //获取表头信息
    private String getCom(){
        String text = this.year+"_"+this.entity+"_"+this.hLine+"_"+this.yLine+"_"+
                      this.pLine+"_"+this.pname+"_"+this.version+"_"+this.proType;
        return text;
    }
    //构造列的map
    private LinkedHashMap<String,String> getLabelMap(String startTime,String endTime){
        LinkedHashMap<String,String> labelMap = new LinkedHashMap<String,String>();
        if(startTime == null || endTime == null){
            return  labelMap;    
        }
        labelMap.put("WBS", "WBS");
        labelMap.put("网络号", "NETWORK");
        labelMap.put("作业号", "WORK_CODE");
        labelMap.put("作业活动","WORK");
        labelMap.put("预算项编码", "TERM_CODE");
        labelMap.put("预算项","TERM");
        labelMap.put("预算科目", "COST_DETAIL");
        labelMap.put("工作中心","CENTER");
        labelMap.put("作业类型","WORK_TYPE");
        labelMap.put("物料编码","BOM_CODE");
        labelMap.put("单位","UNIT");
        labelMap.put("计划成本","PLAN_COST");
        //labelMap.put("KEY9", "OCCURRED");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM");
        List<Date> monthList;
        Date start;
        Date end ;
        try {
            start = sdf.parse(startTime);
            end = sdf.parse(endTime);
            monthList = this.findDates(start, end);
            for(int i = 0 ; i < monthList.size() ; i++){
                labelMap.put(sdf.format(monthList.get(i)), "Y"+sdf.format(monthList.get(i)));
            }
            //labelMap.put("KEY"+(monthList.size()+10),"SUM_AFTER_JUL");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean isReadonly = true;
        this.pcColsDef.clear();
        this.pcColsEx.clear();
        int flag = 1;
        for(Map.Entry<String,String> map:labelMap.entrySet()){
//            if(flag>11){
//                isReadonly = false;
//            }
            if(flag<=11){
                PcColumnDef newCol = new PcColumnDef(map.getKey(),map.getValue(),isReadonly,"");
                this.pcColsDef.add(newCol);
                this.pcColsEx.add(newCol);
            }else{
                PcColumnDef newCol = new PcColumnDef(map.getKey(),map.getValue(),isReadonly,"NUMBER");
                this.pcColsDef.add(newCol);
                this.pcColsEx.add(newCol);
            }
            flag++;
        }
        this.pcColsDef.add(new PcColumnDef("ROW_ID","ROW_ID",false,""));
        this.pcColsDef.add(new PcColumnDef("DATA_TYPE","DATA_TYPE",false,""));
        this.pcColsEx.add(new PcColumnDef("ROW_ID","ROW_ID",false,""));
        this.pcColsEx.add((new PcColumnDef("ACC_CODE","ACC_CODE",false,"")));
        this.pcColsEx.add(new PcColumnDef("CENTER_CODE","CENTER_CODE",false,""));
        this.pcColsEx.add(new PcColumnDef("LGF_NUM","LGF_NUM",false,"NUMBER"));
        this.pcColsEx.add(new PcColumnDef("LGF_TYPE","LGF_TYPE",false,""));
        this.pcColsEx.add(new PcColumnDef("PLAN_QUANTITY","PLAN_QUANTITY",false,"NUMBER"));
        this.pcColsEx.add(new PcColumnDef("PLAN_AMOUNT","PLAN_AMOUNT",false,"NUMBER"));
        this.pcColsEx.add(new PcColumnDef("OCCURRED_QUANTITY","OCCURRED_QUANTITY",false,"NUMBER"));
        this.pcColsEx.add(new PcColumnDef("OCCURRED_AMOUNT","OCCURRED_AMOUNT",false,"NUMBER"));
        this.pcColsEx.add(new PcColumnDef("OCCURRED","OCCURRED",false,"NUMBER"));
        this.pcColsEx.add(new PcColumnDef("DATA_TYPE","DATA_TYPE",false,""));
        ((PcDataTableModel)this.dataModel).setPcColsDef(this.pcColsDef);
        return labelMap;
    }
    
    //时间段中的所有时间
    public static List<Date> findDates(Date dBegin, Date dEnd) {  
            List lDate = new ArrayList();  
            lDate.add(dBegin);  
            Calendar calBegin = Calendar.getInstance();  
            // 使用给定的 Date 设置此 Calendar 的时间    
            calBegin.setTime(dBegin);  
            Calendar calEnd = Calendar.getInstance();  
            // 使用给定的 Date 设置此 Calendar 的时间    
            calEnd.setTime(dEnd);  
            // 测试此日期是否在指定日期之后    
            while (dEnd.after(calBegin.getTime())) {  
                // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
                calBegin.add(Calendar.MONTH, 1);
                lDate.add(calBegin.getTime());
            }  
            return lDate;  
        } 
    
    //校验程序
    public boolean validation(){
        boolean flag = true;
        DBTransaction trans = (DBTransaction)DmsUtils.getDcmApplicationModule().getDBTransaction();
        CallableStatement cs = trans.createCallableStatement("{CALl DMS_ZZX.CHANGE_VALIDATION(?,?,?)}", 0);
        try {
            cs.setString(1, this.curUser.getId());
            cs.setString(2,this.TYPE_CHANGE);
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.execute();
            if("N".equals(cs.getString(3))){
                flag = false;
            }
            cs.close();
            trans.commitAndSaveChangeSet();
        } catch (SQLException e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }
    
    //导入程序
    public void inputPro(){
        DBTransaction trans = (DBTransaction)DmsUtils.getDcmApplicationModule().getDBTransaction();
        CallableStatement cs = trans.createCallableStatement("{CALl DMS_ZZX.CHANGE_INPUTPRO(?)}", 0);
        try {
            cs.setString(1,this.curUser.getId() );
            cs.execute();
            trans.commit();
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //
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

    public void errorPop(ActionEvent actionEvent) {
        this.showErrorPop();
    }

    //导出
    public void operation_export(FacesContext facesContext, OutputStream outputStream) {
        this.dataExportWnd.cancel();
        String type = this.isXlsx ? "xlsx" : "xls";
        try {
            if("xls".equals(type)){
                PcExcel2003WriterImpl writer = new PcExcel2003WriterImpl(
                                                   this.querySql(this.getLabelMap(pStart, pEnd)),
                                                   "合同变更后的基准计划成本",
                                                    this.pcColsEx,
                                                    outputStream);
                writer.writeToFile();
            }else{
                PcExcel2007WriterImpl writer = new PcExcel2007WriterImpl(
                                                    this.querySql(this.getLabelMap(pStart, pEnd)),
                                                    2,this.pcColsEx);
                writer.process(outputStream, "合同变更后的基准计划成本");
                outputStream.flush();
            }
        } catch (Exception e) {
            this._logger.severe(e);
        } 
        dmsLog.operationLog(this.curUser.getAcc(),this.connectId,this.getCom(),"EXPORT");
    }
    //导出文件名
    public String getExportDataExcelName(){
        if(isXlsx){
            return "合同变更后的基准计划成本_"+this.connectId+".xlsx";
        }else{
            return "合同变更后的基准计划成本_"+this.connectId+".xls";
        }
    }
    //显示错误框
    public void showErrorPop(){
        ViewObject vo = ADFUtils.findIterator("ProPlanCostViewIterator").getViewObject();
        vo.setNamedWhereClauseParam("dataType", "BASE");
        RichPopup.PopupHints ph = new RichPopup.PopupHints();
        vo.executeQuery();
        this.errorWindow.show(ph);
    }
    //excel导入
    public void operation_import(ActionEvent actionEvent) {
        DBTransaction trans = (DBTransaction)DmsUtils.getDcmApplicationModule().getDBTransaction();
        this.dataImportWnd.cancel();
        //表头为空
        if(this.year==null||this.pname==null||this.version==null){
            JSFUtils.addFacesErrorMessage("请选择表头信息");
            return;
        }
        //上传文件为空
        if (null == this.fileInput.getValue()) {
            JSFUtils.addFacesErrorMessage(DmsUtils.getMsg("dcm.plz_select_import_file"));
            return;
        }
        //获取文件上传路径
        String filePath = this.uploadFile();
        
        if (null == filePath) {
            return;
        }
        //读取excel数据到数据库临时表
        try {
            if (!this.handleExcel(filePath, connectId)) {
            return;
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.fileInput.resetValue();
        
        //删除原有新增数据,否则校验报错
        Statement statDelete = trans.createStatement(DBTransaction.DEFAULT);
        String sqlDelete = "DELETE FROM PRO_PLAN_COST_BODY WHERE CONNECT_ID='"+this.connectId+"'" +
            " AND DATA_TYPE IS NULL";
        try {
            statDelete.executeUpdate(sqlDelete);
            trans.commit();
            statDelete.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //校验程序
            if(this.validation_import()){
                if(this.validation()){
                    
                    //插入body表
                    this.inputPro_import();
                }else{
                    this.showErrorPop();
                }
            }else {
            //若出现错误则显示错误信息提示框
                this.showErrorPop();
        }
        //刷新数据
        this.createTableModel(pStart , newEnd);
        dmsLog.operationLog(this.curUser.getAcc(),this.connectId,this.getCom(),"IMPORT");
    }
    //excel导入导入程序
    public void inputPro_import(){
        DBTransaction trans = (DBTransaction)DmsUtils.getDcmApplicationModule().getDBTransaction();
        CallableStatement cs = trans.createCallableStatement("{CALl DMS_ZZX.CHANGEIMPORT_INPUTPRO(?,?)}", 0);
        try {
            cs.setString(1,this.curUser.getId() );
            cs.setString(2,this.connectId);
            cs.execute();
            trans.commit();
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //excel导入校验程序
    public boolean validation_import(){
        boolean flag = true;
        DBTransaction trans = (DBTransaction)DmsUtils.getDcmApplicationModule().getDBTransaction();
        CallableStatement cs = trans.createCallableStatement("{CALl DMS_ZZX.CHANGEIMPORT_VALIDATION(?,?,?,?)}", 0);
        try {
            cs.setString(1, this.curUser.getId());
            cs.setString(2, "BASE");
            cs.setString(3,this.connectId);
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.execute();
            if("N".equals(cs.getString(4))){
                flag = false;
            }
            cs.close();
            trans.commit();
        } catch (SQLException e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }
    
    //读取excel数据到临时表
    private boolean handleExcel(String fileName, String curComRecordId) throws SQLException {
        DBTransaction trans =(DBTransaction)DmsUtils.getDcmApplicationModule().getTransaction();
        //清空已有临时表数据
        this.deleteTempAndError();
        UploadedFile file = (UploadedFile)this.fileInput.getValue();
        String fname = file.getFilename();
        String name = fname.substring(fname.indexOf("_")+1, fname.indexOf("."));
        if(!name.equals(this.connectId)){
            JSFUtils.addFacesErrorMessage("请选择正确的文件");
            return false;
        }
        SPRowReader spReader = new SPRowReader(trans,2,this.connectId,this.pcColsEx,this.curUser.getId(),this.TYPE_CHANGE, name);
        try {
                ExcelReaderUtil.readExcel(spReader, fileName, true);
                spReader.close();
            } catch (Exception e) {
                this._logger.severe(e);
                JSFUtils.addFacesErrorMessage(DmsUtils.getMsg("dcm.excel_handle_error"));
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
            JSFUtils.addFacesErrorMessage(msg);
            return null;
        }
        String fileExtension =file.getFilename().substring(file.getFilename().lastIndexOf("."));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = dateFormat.format(new Date());
        File dmsBaseDir = new File("DMS/UPLOAD/合同变更" );
        //如若文件路径不存在则创建文件目录
        if (!dmsBaseDir.exists()) {
            dmsBaseDir.mkdirs();
        }
        String fileName = "DMS/UPLOAD/" + "合同变更" + "/"+file.getFilename();
        try {
            InputStream inputStream = file.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(fileName);
            BufferedInputStream bufferedInputStream =new BufferedInputStream(inputStream);
            BufferedOutputStream bufferedOutputStream =
                new BufferedOutputStream(outputStream);
            byte[] buffer = new byte[10240];
            int bytesRead = 0;
            while ((bytesRead = bufferedInputStream.read(buffer, 0, 10240)) !=-1) {
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
            JSFUtils.addFacesErrorMessage(msg);
            return null;
        }
        return (new File(fileName)).getAbsolutePath();
    }
    
    public void outBlock(ActionEvent actionEvent) {
        DBTransaction trans = (DBTransaction)DmsUtils.getDcmApplicationModule().getTransaction();
        Statement stat = trans.createStatement(DBTransaction.DEFAULT);
        String sql = "UPDATE PRO_PLAN_COST_HEADER SET(DATA_TYPE) = 'CHANGE' WHERE CONNECT_ID = '"+this.connectId+"'";
        try {
            stat.executeUpdate(sql);
            trans.commit();
            stat.close();
            this.isEdited = false;
            this.isChange = false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Statement statType = trans.createStatement(DBTransaction.DEFAULT);
        String sqlType = "UPDATE PRO_PLAN_COST_BODY SET(DATA_TYPE) = '' WHERE CONNECT_ID='"+this.connectId+"'";
        try {
            statType.executeUpdate(sqlType);
            trans.commit();
            statType.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void setPanelaCollection(RichPanelCollection panelaCollection) {
        this.panelaCollection = panelaCollection;
    }

    public RichPanelCollection getPanelaCollection() {
        return panelaCollection;
    }

    public void setDataModel(CollectionModel dataModel) {
        this.dataModel = dataModel;
    }

    public CollectionModel getDataModel() {
        return dataModel;
    }

    public void setPcColsDef(List<PcColumnDef> pcColsDef) {
        this.pcColsDef = pcColsDef;
    }

    public List<PcColumnDef> getPcColsDef() {
        return pcColsDef;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setYearList(List<SelectItem> yearList) {
        this.yearList = yearList;
    }

    public List<SelectItem> getYearList() {
        return yearList;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getEntity() {
        return entity;
    }

    public void setPLine(String pLine) {
        this.pLine = pLine;
    }

    public String getPLine() {
        return pLine;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPname() {
        return pname;
    }

    public void setPnameList(List<SelectItem> pnameList) {
        this.pnameList = pnameList;
    }

    public List<SelectItem> getPnameList() {
        return pnameList;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersionList(List<SelectItem> versionList) {
        this.versionList = versionList;
    }

    public List<SelectItem> getVersionList() {
        return versionList;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getProType() {
        return proType;
    }

    public void setHLine(String hLine) {
        this.hLine = hLine;
    }

    public String getHLine() {
        return hLine;
    }

    public void setYLine(String yLine) {
        this.yLine = yLine;
    }

    public String getYLine() {
        return yLine;
    }

    public void setPStart(String pStart) {
        this.pStart = pStart;
    }

    public String getPStart() {
        return pStart;
    }

    public void setPEnd(String pEnd) {
        this.pEnd = pEnd;
    }

    public String getPEnd() {
        return pEnd;
    }

    public void setConnectId(String connectId) {
        this.connectId = connectId;
    }

    public String getConnectId() {
        return connectId;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewEnd(String newEnd) {
        this.newEnd = newEnd;
    }

    public String getNewEnd() {
        return newEnd;
    }

    public void setIsXlsx(boolean isXlsx) {
        this.isXlsx = isXlsx;
    }

    public boolean isIsXlsx() {
        return isXlsx;
    }

    public void setDataExportWnd(RichPopup dataExportWnd) {
        this.dataExportWnd = dataExportWnd;
    }

    public RichPopup getDataExportWnd() {
        return dataExportWnd;
    }

    public void setErrorWindow(RichPopup errorWindow) {
        this.errorWindow = errorWindow;
    }

    public RichPopup getErrorWindow() {
        return errorWindow;
    }

    public void setFileInput(RichInputFile fileInput) {
        this.fileInput = fileInput;
    }

    public RichInputFile getFileInput() {
        return fileInput;
    }

    public void setDataImportWnd(RichPopup dataImportWnd) {
        this.dataImportWnd = dataImportWnd;
    }

    public RichPopup getDataImportWnd() {
        return dataImportWnd;
    }

    public void setPcColsEx(List<PcColumnDef> pcColsEx) {
        this.pcColsEx = pcColsEx;
    }

    public List<PcColumnDef> getPcColsEx() {
        return pcColsEx;
    }

    public void setProLov(DmsComBoxLov proLov) {
        this.proLov = proLov;
    }

    public DmsComBoxLov getProLov() {
        return proLov;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isIsSelected() {
        return isSelected;
    }

    public void setIsEdited(boolean isEdited) {
        this.isEdited = isEdited;
    }

    public boolean isIsEdited() {
        return isEdited;
    }

    public void setIsChange(boolean isChange) {
        this.isChange = isChange;
    }

    public boolean isIsChange() {
        return isChange;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    public boolean isIsManager() {
        return isManager;
    }
}

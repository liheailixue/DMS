package team.epm.dms.view;

import oracle.adf.share.ADFContext;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Dec 18 16:37:03 CST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DmsValueSetViewImpl extends ViewObjectImpl {
    private static ADFLogger logger=ADFLogger.createADFLogger(DmsValueSetViewImpl.class);
    /**
     * This is the default constructor (do not remove).
     */
    public DmsValueSetViewImpl() {
    }

    /**
     * Returns the bind variable value for locale.
     * @return bind variable value for locale
     */
    public String getlocale() {
        return (String)getNamedWhereClauseParam("locale");
    }

    /**
     * Sets <code>value</code> for bind variable locale.
     * @param value value to bind as locale
     */
    public void setlocale(String value) {
        setNamedWhereClauseParam("locale", value);
    }
    public ViewObject getUnAssignedValueObject(){
        ViewObject vo=null;
        Row row=this.getCurrentRow();
        if(row!=null){
            String viewName="UnAssignedVs"+row.getAttribute("Id")+"Vo";
            if(this.getApplicationModule().findViewObject(viewName)!=null){
                vo=this.getApplicationModule().findViewObject(viewName);
            }else{
                StringBuffer sql=new StringBuffer();
                sql.append("SELECT T.CODE,T.MEANING FROM \"");
                sql.append(row.getAttribute("Source").toString().toUpperCase());
                sql.append("\" T");
                sql.append(" WHERE T.LOCALE='");
                sql.append(ADFContext.getCurrent().getLocale());
                sql.append("'");
                sql.append(" AND NOT EXISTS (SELECT 1 FROM DMS_ROLE_VALUE RV WHERE RV.ROLE_ID = :roleId");
                sql.append(" AND RV.VALUE_ID = T.CODE");
                sql.append(" AND RV.VALUE_SET_ID = '");
                sql.append(row.getAttribute("Id"));
                sql.append("'");
                sql.append(")");
                sql.append(" ORDER BY IDX");
                vo=this.getApplicationModule().createViewObjectFromQueryStmt(viewName, sql.toString());
                vo.defineNamedWhereClauseParam("roleId", null, null);
            }
        }
        return vo;
    }
}

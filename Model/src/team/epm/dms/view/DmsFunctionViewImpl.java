package team.epm.dms.view;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Nov 10 16:16:39 CST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DmsFunctionViewImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public DmsFunctionViewImpl() {
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
}

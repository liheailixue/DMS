package team.epm.dms.view.client;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.client.remote.ViewUsageImpl;

import team.epm.dms.view.common.DmsUserView;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Oct 14 17:07:34 CST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DmsUserViewClient extends ViewUsageImpl implements DmsUserView {
    /**
     * This is the default constructor (do not remove).
     */
    public DmsUserViewClient() {
    }

    public RowSetIterator queryCurUser() {
        Object _ret =
            getApplicationModuleProxy().riInvokeExportedMethod(this,"queryCurUser",null,null);
        return (RowSetIterator)_ret;
    }
}

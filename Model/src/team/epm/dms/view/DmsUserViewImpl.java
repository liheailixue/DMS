package team.epm.dms.view;

import oracle.adf.share.ADFContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.server.ViewObjectImpl;

import team.epm.dms.view.common.DmsUserView;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Sep 29 12:14:23 CST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DmsUserViewImpl extends ViewObjectImpl implements DmsUserView {
    /**
     * This is the default constructor (do not remove).
     */
    public DmsUserViewImpl() {
    }
    public RowSetIterator queryCurUser(){
        String userId=this.getApplicationModule().getSession().getUserData().get("userId")+"";
        ViewCriteria vc= this.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        vcRow.setAttribute("Id", userId);
        vc.addElement(vcRow);
        this.applyViewCriteria(vc);
        this.executeQuery();
        return   this.getRowSetIterator();
    }
}

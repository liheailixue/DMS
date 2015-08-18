package team.epm.dms.view;

import oracle.jbo.RowSet;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Aug 14 21:57:56 CST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DmsApproveTemplateStatusVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        ApprovalStatus {
            public Object get(DmsApproveTemplateStatusVORowImpl obj) {
                return obj.getApprovalStatus();
            }

            public void put(DmsApproveTemplateStatusVORowImpl obj,
                            Object value) {
                obj.setApprovalStatus((String)value);
            }
        }
        ,
        CreatedAt {
            public Object get(DmsApproveTemplateStatusVORowImpl obj) {
                return obj.getCreatedAt();
            }

            public void put(DmsApproveTemplateStatusVORowImpl obj,
                            Object value) {
                obj.setCreatedAt((Date)value);
            }
        }
        ,
        CreatedBy {
            public Object get(DmsApproveTemplateStatusVORowImpl obj) {
                return obj.getCreatedBy();
            }

            public void put(DmsApproveTemplateStatusVORowImpl obj,
                            Object value) {
                obj.setCreatedBy((String)value);
            }
        }
        ,
        EntityId {
            public Object get(DmsApproveTemplateStatusVORowImpl obj) {
                return obj.getEntityId();
            }

            public void put(DmsApproveTemplateStatusVORowImpl obj,
                            Object value) {
                obj.setEntityId((String)value);
            }
        }
        ,
        Meaning {
            public Object get(DmsApproveTemplateStatusVORowImpl obj) {
                return obj.getMeaning();
            }

            public void put(DmsApproveTemplateStatusVORowImpl obj,
                            Object value) {
                obj.setMeaning((String)value);
            }
        }
        ,
        FinishAt {
            public Object get(DmsApproveTemplateStatusVORowImpl obj) {
                return obj.getFinishAt();
            }

            public void put(DmsApproveTemplateStatusVORowImpl obj,
                            Object value) {
                obj.setFinishAt((Date)value);
            }
        }
        ,
        Id {
            public Object get(DmsApproveTemplateStatusVORowImpl obj) {
                return obj.getId();
            }

            public void put(DmsApproveTemplateStatusVORowImpl obj,
                            Object value) {
                obj.setId((String)value);
            }
        }
        ,
        Locale {
            public Object get(DmsApproveTemplateStatusVORowImpl obj) {
                return obj.getLocale();
            }

            public void put(DmsApproveTemplateStatusVORowImpl obj,
                            Object value) {
                obj.setLocale((String)value);
            }
        }
        ,
        PersonId {
            public Object get(DmsApproveTemplateStatusVORowImpl obj) {
                return obj.getPersonId();
            }

            public void put(DmsApproveTemplateStatusVORowImpl obj,
                            Object value) {
                obj.setPersonId((String)value);
            }
        }
        ,
        Name {
            public Object get(DmsApproveTemplateStatusVORowImpl obj) {
                return obj.getName();
            }

            public void put(DmsApproveTemplateStatusVORowImpl obj,
                            Object value) {
                obj.setName((String)value);
            }
        }
        ,
        RunId {
            public Object get(DmsApproveTemplateStatusVORowImpl obj) {
                return obj.getRunId();
            }

            public void put(DmsApproveTemplateStatusVORowImpl obj,
                            Object value) {
                obj.setRunId((String)value);
            }
        }
        ,
        StepNo {
            public Object get(DmsApproveTemplateStatusVORowImpl obj) {
                return obj.getStepNo();
            }

            public void put(DmsApproveTemplateStatusVORowImpl obj,
                            Object value) {
                obj.setStepNo((Number)value);
            }
        }
        ,
        TemplateId {
            public Object get(DmsApproveTemplateStatusVORowImpl obj) {
                return obj.getTemplateId();
            }

            public void put(DmsApproveTemplateStatusVORowImpl obj,
                            Object value) {
                obj.setTemplateId((String)value);
            }
        }
        ,
        UpdatedAt {
            public Object get(DmsApproveTemplateStatusVORowImpl obj) {
                return obj.getUpdatedAt();
            }

            public void put(DmsApproveTemplateStatusVORowImpl obj,
                            Object value) {
                obj.setUpdatedAt((Date)value);
            }
        }
        ,
        UpdatedBy {
            public Object get(DmsApproveTemplateStatusVORowImpl obj) {
                return obj.getUpdatedBy();
            }

            public void put(DmsApproveTemplateStatusVORowImpl obj,
                            Object value) {
                obj.setUpdatedBy((String)value);
            }
        }
        ,
        ComId {
            public Object get(DmsApproveTemplateStatusVORowImpl obj) {
                return obj.getComId();
            }

            public void put(DmsApproveTemplateStatusVORowImpl obj,
                            Object value) {
                obj.setComId((String)value);
            }
        }
        ,
        DmsDimEntitysVA {
            public Object get(DmsApproveTemplateStatusVORowImpl obj) {
                return obj.getDmsDimEntitysVA();
            }

            public void put(DmsApproveTemplateStatusVORowImpl obj,
                            Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DmsUserVA {
            public Object get(DmsApproveTemplateStatusVORowImpl obj) {
                return obj.getDmsUserVA();
            }

            public void put(DmsApproveTemplateStatusVORowImpl obj,
                            Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(DmsApproveTemplateStatusVORowImpl object);

        public abstract void put(DmsApproveTemplateStatusVORowImpl object,
                                 Object value);

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int APPROVALSTATUS = AttributesEnum.ApprovalStatus.index();
    public static final int CREATEDAT = AttributesEnum.CreatedAt.index();
    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int ENTITYID = AttributesEnum.EntityId.index();
    public static final int MEANING = AttributesEnum.Meaning.index();
    public static final int FINISHAT = AttributesEnum.FinishAt.index();
    public static final int ID = AttributesEnum.Id.index();
    public static final int LOCALE = AttributesEnum.Locale.index();
    public static final int PERSONID = AttributesEnum.PersonId.index();
    public static final int NAME = AttributesEnum.Name.index();
    public static final int RUNID = AttributesEnum.RunId.index();
    public static final int STEPNO = AttributesEnum.StepNo.index();
    public static final int TEMPLATEID = AttributesEnum.TemplateId.index();
    public static final int UPDATEDAT = AttributesEnum.UpdatedAt.index();
    public static final int UPDATEDBY = AttributesEnum.UpdatedBy.index();
    public static final int COMID = AttributesEnum.ComId.index();
    public static final int DMSDIMENTITYSVA = AttributesEnum.DmsDimEntitysVA.index();
    public static final int DMSUSERVA = AttributesEnum.DmsUserVA.index();

    /**
     * This is the default constructor (do not remove).
     */
    public DmsApproveTemplateStatusVORowImpl() {
    }

    /**
     * Gets DmsApproveTemplateStatusEO entity object.
     * @return the DmsApproveTemplateStatusEO
     */
    public EntityImpl getDmsApproveTemplateStatusEO() {
        return (EntityImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for APPROVAL_STATUS using the alias name ApprovalStatus.
     * @return the APPROVAL_STATUS
     */
    public String getApprovalStatus() {
        return (String) getAttributeInternal(APPROVALSTATUS);
    }

    /**
     * Sets <code>value</code> as attribute value for APPROVAL_STATUS using the alias name ApprovalStatus.
     * @param value value to set the APPROVAL_STATUS
     */
    public void setApprovalStatus(String value) {
        setAttributeInternal(APPROVALSTATUS, value);
    }

    /**
     * Gets the attribute value for CREATED_AT using the alias name CreatedAt.
     * @return the CREATED_AT
     */
    public Date getCreatedAt() {
        return (Date) getAttributeInternal(CREATEDAT);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATED_AT using the alias name CreatedAt.
     * @param value value to set the CREATED_AT
     */
    public void setCreatedAt(Date value) {
        setAttributeInternal(CREATEDAT, value);
    }

    /**
     * Gets the attribute value for CREATED_BY using the alias name CreatedBy.
     * @return the CREATED_BY
     */
    public String getCreatedBy() {
        return (String) getAttributeInternal(CREATEDBY);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATED_BY using the alias name CreatedBy.
     * @param value value to set the CREATED_BY
     */
    public void setCreatedBy(String value) {
        setAttributeInternal(CREATEDBY, value);
    }

    /**
     * Gets the attribute value for ENTITY_ID using the alias name EntityId.
     * @return the ENTITY_ID
     */
    public String getEntityId() {
        return (String) getAttributeInternal(ENTITYID);
    }

    /**
     * Sets <code>value</code> as attribute value for ENTITY_ID using the alias name EntityId.
     * @param value value to set the ENTITY_ID
     */
    public void setEntityId(String value) {
        setAttributeInternal(ENTITYID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Meaning.
     * @return the Meaning
     */
    public String getMeaning() {
        return (String) getAttributeInternal(MEANING);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Meaning.
     * @param value value to set the  Meaning
     */
    public void setMeaning(String value) {
        setAttributeInternal(MEANING, value);
    }

    /**
     * Gets the attribute value for FINISH_AT using the alias name FinishAt.
     * @return the FINISH_AT
     */
    public Date getFinishAt() {
        return (Date) getAttributeInternal(FINISHAT);
    }

    /**
     * Sets <code>value</code> as attribute value for FINISH_AT using the alias name FinishAt.
     * @param value value to set the FINISH_AT
     */
    public void setFinishAt(Date value) {
        setAttributeInternal(FINISHAT, value);
    }

    /**
     * Gets the attribute value for ID using the alias name Id.
     * @return the ID
     */
    public String getId() {
        return (String) getAttributeInternal(ID);
    }

    /**
     * Sets <code>value</code> as attribute value for ID using the alias name Id.
     * @param value value to set the ID
     */
    public void setId(String value) {
        setAttributeInternal(ID, value);
    }

    /**
     * Gets the attribute value for LOCALE using the alias name Locale.
     * @return the LOCALE
     */
    public String getLocale() {
        return (String) getAttributeInternal(LOCALE);
    }

    /**
     * Sets <code>value</code> as attribute value for LOCALE using the alias name Locale.
     * @param value value to set the LOCALE
     */
    public void setLocale(String value) {
        setAttributeInternal(LOCALE, value);
    }

    /**
     * Gets the attribute value for PERSON_ID using the alias name PersonId.
     * @return the PERSON_ID
     */
    public String getPersonId() {
        return (String) getAttributeInternal(PERSONID);
    }

    /**
     * Sets <code>value</code> as attribute value for PERSON_ID using the alias name PersonId.
     * @param value value to set the PERSON_ID
     */
    public void setPersonId(String value) {
        setAttributeInternal(PERSONID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Name.
     * @return the Name
     */
    public String getName() {
        return (String) getAttributeInternal(NAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Name.
     * @param value value to set the  Name
     */
    public void setName(String value) {
        setAttributeInternal(NAME, value);
    }

    /**
     * Gets the attribute value for RUN_ID using the alias name RunId.
     * @return the RUN_ID
     */
    public String getRunId() {
        return (String) getAttributeInternal(RUNID);
    }

    /**
     * Sets <code>value</code> as attribute value for RUN_ID using the alias name RunId.
     * @param value value to set the RUN_ID
     */
    public void setRunId(String value) {
        setAttributeInternal(RUNID, value);
    }

    /**
     * Gets the attribute value for STEP_NO using the alias name StepNo.
     * @return the STEP_NO
     */
    public Number getStepNo() {
        return (Number) getAttributeInternal(STEPNO);
    }

    /**
     * Sets <code>value</code> as attribute value for STEP_NO using the alias name StepNo.
     * @param value value to set the STEP_NO
     */
    public void setStepNo(Number value) {
        setAttributeInternal(STEPNO, value);
    }

    /**
     * Gets the attribute value for TEMPLATE_ID using the alias name TemplateId.
     * @return the TEMPLATE_ID
     */
    public String getTemplateId() {
        return (String) getAttributeInternal(TEMPLATEID);
    }

    /**
     * Sets <code>value</code> as attribute value for TEMPLATE_ID using the alias name TemplateId.
     * @param value value to set the TEMPLATE_ID
     */
    public void setTemplateId(String value) {
        setAttributeInternal(TEMPLATEID, value);
    }

    /**
     * Gets the attribute value for UPDATED_AT using the alias name UpdatedAt.
     * @return the UPDATED_AT
     */
    public Date getUpdatedAt() {
        return (Date) getAttributeInternal(UPDATEDAT);
    }

    /**
     * Sets <code>value</code> as attribute value for UPDATED_AT using the alias name UpdatedAt.
     * @param value value to set the UPDATED_AT
     */
    public void setUpdatedAt(Date value) {
        setAttributeInternal(UPDATEDAT, value);
    }

    /**
     * Gets the attribute value for UPDATED_BY using the alias name UpdatedBy.
     * @return the UPDATED_BY
     */
    public String getUpdatedBy() {
        return (String) getAttributeInternal(UPDATEDBY);
    }

    /**
     * Sets <code>value</code> as attribute value for UPDATED_BY using the alias name UpdatedBy.
     * @param value value to set the UPDATED_BY
     */
    public void setUpdatedBy(String value) {
        setAttributeInternal(UPDATEDBY, value);
    }

    /**
     * Gets the attribute value for COM_ID using the alias name ComId.
     * @return the COM_ID
     */
    public String getComId() {
        return (String) getAttributeInternal(COMID);
    }

    /**
     * Sets <code>value</code> as attribute value for COM_ID using the alias name ComId.
     * @param value value to set the COM_ID
     */
    public void setComId(String value) {
        setAttributeInternal(COMID, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> DmsDimEntitysVA.
     */
    public RowSet getDmsDimEntitysVA() {
        return (RowSet)getAttributeInternal(DMSDIMENTITYSVA);
    }

    /**
     * Gets the view accessor <code>RowSet</code> DmsUserVA.
     */
    public RowSet getDmsUserVA() {
        return (RowSet)getAttributeInternal(DMSUSERVA);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index,
                                           AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            return AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].get(this);
        }
        return super.getAttrInvokeAccessor(index, attrDef);
    }

    /**
     * setAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param value the value to assign to the attribute
     * @param attrDef the attribute

     * @throws Exception
     */
    protected void setAttrInvokeAccessor(int index, Object value,
                                         AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }
}

package team.epm.dms.model;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;

import org.apache.commons.lang.ObjectUtils;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Apr 13 20:36:07 CST 2016
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DmsApprovalflowEntitysEOImpl extends EntityImpl {
    private static EntityDefImpl mDefinitionObject;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Id {
            public Object get(DmsApprovalflowEntitysEOImpl obj) {
                return obj.getId();
            }

            public void put(DmsApprovalflowEntitysEOImpl obj, Object value) {
                obj.setId((String)value);
            }
        }
        ,
        ApprovalId {
            public Object get(DmsApprovalflowEntitysEOImpl obj) {
                return obj.getApprovalId();
            }

            public void put(DmsApprovalflowEntitysEOImpl obj, Object value) {
                obj.setApprovalId((String)value);
            }
        }
        ,
        TempId {
            public Object get(DmsApprovalflowEntitysEOImpl obj) {
                return obj.getTempId();
            }

            public void put(DmsApprovalflowEntitysEOImpl obj, Object value) {
                obj.setTempId((String)value);
            }
        }
        ,
        EntityCode {
            public Object get(DmsApprovalflowEntitysEOImpl obj) {
                return obj.getEntityCode();
            }

            public void put(DmsApprovalflowEntitysEOImpl obj, Object value) {
                obj.setEntityCode((String)value);
            }
        }
        ,
        UserId {
            public Object get(DmsApprovalflowEntitysEOImpl obj) {
                return obj.getUserId();
            }

            public void put(DmsApprovalflowEntitysEOImpl obj, Object value) {
                obj.setUserId((String)value);
            }
        }
        ,
        Seq {
            public Object get(DmsApprovalflowEntitysEOImpl obj) {
                return obj.getSeq();
            }

            public void put(DmsApprovalflowEntitysEOImpl obj, Object value) {
                obj.setSeq((Number)value);
            }
        }
        ,
        CreatedAt {
            public Object get(DmsApprovalflowEntitysEOImpl obj) {
                return obj.getCreatedAt();
            }

            public void put(DmsApprovalflowEntitysEOImpl obj, Object value) {
                obj.setCreatedAt((Date)value);
            }
        }
        ,
        UpdatedAt {
            public Object get(DmsApprovalflowEntitysEOImpl obj) {
                return obj.getUpdatedAt();
            }

            public void put(DmsApprovalflowEntitysEOImpl obj, Object value) {
                obj.setUpdatedAt((Date)value);
            }
        }
        ,
        CreatedBy {
            public Object get(DmsApprovalflowEntitysEOImpl obj) {
                return obj.getCreatedBy();
            }

            public void put(DmsApprovalflowEntitysEOImpl obj, Object value) {
                obj.setCreatedBy((String)value);
            }
        }
        ,
        UpdatedBy {
            public Object get(DmsApprovalflowEntitysEOImpl obj) {
                return obj.getUpdatedBy();
            }

            public void put(DmsApprovalflowEntitysEOImpl obj, Object value) {
                obj.setUpdatedBy((String)value);
            }
        }
        ,
        ComRecordId {
            public Object get(DmsApprovalflowEntitysEOImpl obj) {
                return obj.getComRecordId();
            }

            public void put(DmsApprovalflowEntitysEOImpl obj, Object value) {
                obj.setComRecordId((String)value);
            }
        }
        ,
        Idx {
            public Object get(DmsApprovalflowEntitysEOImpl obj) {
                return obj.getIdx();
            }

            public void put(DmsApprovalflowEntitysEOImpl obj, Object value) {
                obj.setIdx((Number)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(DmsApprovalflowEntitysEOImpl object);

        public abstract void put(DmsApprovalflowEntitysEOImpl object,
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

    @Override
    protected void prepareForDML(int operation, TransactionEvent transactionEvent) {
        super.prepareForDML(operation, transactionEvent);
        if (operation==DML_UPDATE){
            this.setUpdatedAt(new Date(new java.sql.Timestamp(System.currentTimeMillis())));
            this.setUpdatedBy(ObjectUtils.toString(this.getDBTransaction().getSession().getUserData().get("userId")));
        }
    }

    public static final int ID = AttributesEnum.Id.index();
    public static final int APPROVALID = AttributesEnum.ApprovalId.index();
    public static final int TEMPID = AttributesEnum.TempId.index();
    public static final int ENTITYCODE = AttributesEnum.EntityCode.index();
    public static final int USERID = AttributesEnum.UserId.index();
    public static final int SEQ = AttributesEnum.Seq.index();
    public static final int CREATEDAT = AttributesEnum.CreatedAt.index();
    public static final int UPDATEDAT = AttributesEnum.UpdatedAt.index();
    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int UPDATEDBY = AttributesEnum.UpdatedBy.index();
    public static final int COMRECORDID = AttributesEnum.ComRecordId.index();
    public static final int IDX = AttributesEnum.Idx.index();

    /**
     * This is the default constructor (do not remove).
     */
    public DmsApprovalflowEntitysEOImpl() {
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        if (mDefinitionObject == null) {
            mDefinitionObject = EntityDefImpl.findDefObject("team.epm.dms.model.DmsApprovalflowEntitysEO");
        }
        return mDefinitionObject;
    }

    /**
     * Gets the attribute value for Id, using the alias name Id.
     * @return the Id
     */
    public String getId() {
        return (String)getAttributeInternal(ID);
    }

    /**
     * Sets <code>value</code> as the attribute value for Id.
     * @param value value to set the Id
     */
    public void setId(String value) {
        setAttributeInternal(ID, value);
    }

    /**
     * Gets the attribute value for ApprovalId, using the alias name ApprovalId.
     * @return the ApprovalId
     */
    public String getApprovalId() {
        return (String)getAttributeInternal(APPROVALID);
    }

    /**
     * Sets <code>value</code> as the attribute value for ApprovalId.
     * @param value value to set the ApprovalId
     */
    public void setApprovalId(String value) {
        setAttributeInternal(APPROVALID, value);
    }

    /**
     * Gets the attribute value for TempId, using the alias name TempId.
     * @return the TempId
     */
    public String getTempId() {
        return (String)getAttributeInternal(TEMPID);
    }

    /**
     * Sets <code>value</code> as the attribute value for TempId.
     * @param value value to set the TempId
     */
    public void setTempId(String value) {
        setAttributeInternal(TEMPID, value);
    }

    /**
     * Gets the attribute value for EntityCode, using the alias name EntityCode.
     * @return the EntityCode
     */
    public String getEntityCode() {
        return (String)getAttributeInternal(ENTITYCODE);
    }

    /**
     * Sets <code>value</code> as the attribute value for EntityCode.
     * @param value value to set the EntityCode
     */
    public void setEntityCode(String value) {
        setAttributeInternal(ENTITYCODE, value);
    }

    /**
     * Gets the attribute value for UserId, using the alias name UserId.
     * @return the UserId
     */
    public String getUserId() {
        return (String)getAttributeInternal(USERID);
    }

    /**
     * Sets <code>value</code> as the attribute value for UserId.
     * @param value value to set the UserId
     */
    public void setUserId(String value) {
        setAttributeInternal(USERID, value);
    }

    /**
     * Gets the attribute value for Seq, using the alias name Seq.
     * @return the Seq
     */
    public Number getSeq() {
        return (Number)getAttributeInternal(SEQ);
    }

    /**
     * Sets <code>value</code> as the attribute value for Seq.
     * @param value value to set the Seq
     */
    public void setSeq(Number value) {
        setAttributeInternal(SEQ, value);
    }

    /**
     * Gets the attribute value for CreatedAt, using the alias name CreatedAt.
     * @return the CreatedAt
     */
    public Date getCreatedAt() {
        return (Date)getAttributeInternal(CREATEDAT);
    }

    /**
     * Sets <code>value</code> as the attribute value for CreatedAt.
     * @param value value to set the CreatedAt
     */
    public void setCreatedAt(Date value) {
        setAttributeInternal(CREATEDAT, value);
    }

    /**
     * Gets the attribute value for UpdatedAt, using the alias name UpdatedAt.
     * @return the UpdatedAt
     */
    public Date getUpdatedAt() {
        return (Date)getAttributeInternal(UPDATEDAT);
    }

    /**
     * Sets <code>value</code> as the attribute value for UpdatedAt.
     * @param value value to set the UpdatedAt
     */
    public void setUpdatedAt(Date value) {
        setAttributeInternal(UPDATEDAT, value);
    }

    /**
     * Gets the attribute value for CreatedBy, using the alias name CreatedBy.
     * @return the CreatedBy
     */
    public String getCreatedBy() {
        return (String)getAttributeInternal(CREATEDBY);
    }

    /**
     * Sets <code>value</code> as the attribute value for CreatedBy.
     * @param value value to set the CreatedBy
     */
    public void setCreatedBy(String value) {
        setAttributeInternal(CREATEDBY, value);
    }

    /**
     * Gets the attribute value for UpdatedBy, using the alias name UpdatedBy.
     * @return the UpdatedBy
     */
    public String getUpdatedBy() {
        return (String)getAttributeInternal(UPDATEDBY);
    }

    /**
     * Sets <code>value</code> as the attribute value for UpdatedBy.
     * @param value value to set the UpdatedBy
     */
    public void setUpdatedBy(String value) {
        setAttributeInternal(UPDATEDBY, value);
    }

    /**
     * Gets the attribute value for ComRecordId, using the alias name ComRecordId.
     * @return the ComRecordId
     */
    public String getComRecordId() {
        return (String)getAttributeInternal(COMRECORDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for ComRecordId.
     * @param value value to set the ComRecordId
     */
    public void setComRecordId(String value) {
        setAttributeInternal(COMRECORDID, value);
    }

    /**
     * Gets the attribute value for Idx, using the alias name Idx.
     * @return the Idx
     */
    public Number getIdx() {
        return (Number)getAttributeInternal(IDX);
    }

    /**
     * Sets <code>value</code> as the attribute value for Idx.
     * @param value value to set the Idx
     */
    public void setIdx(Number value) {
        setAttributeInternal(IDX, value);
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


    /**
     * @param id key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String id) {
        return new Key(new Object[]{id});
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        super.create(attributeList);
    }

    /**
     * Add entity remove logic in this method.
     */
    public void remove() {
        super.remove();
    }

    /**
     * Add locking logic here.
     */
    public void lock() {
        super.lock();
    }

    /**
     * Custom DML update/insert/delete logic here.
     * @param operation the operation type
     * @param e the transaction event
     */
    protected void doDML(int operation, TransactionEvent e) {
        super.doDML(operation, e);
    }
}

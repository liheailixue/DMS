package team.epm.dcm.view;

import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;

import team.epm.dcm.model.DcmTemplateColumnImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Jan 12 14:51:52 CST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DcmTemplateColumnViewRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Id {
            public Object get(DcmTemplateColumnViewRowImpl obj) {
                return obj.getId();
            }

            public void put(DcmTemplateColumnViewRowImpl obj, Object value) {
                obj.setId((String)value);
            }
        }
        ,
        Locale {
            public Object get(DcmTemplateColumnViewRowImpl obj) {
                return obj.getLocale();
            }

            public void put(DcmTemplateColumnViewRowImpl obj, Object value) {
                obj.setLocale((String)value);
            }
        }
        ,
        ColumnLabel {
            public Object get(DcmTemplateColumnViewRowImpl obj) {
                return obj.getColumnLabel();
            }

            public void put(DcmTemplateColumnViewRowImpl obj, Object value) {
                obj.setColumnLabel((String)value);
            }
        }
        ,
        DbTableCol {
            public Object get(DcmTemplateColumnViewRowImpl obj) {
                return obj.getDbTableCol();
            }

            public void put(DcmTemplateColumnViewRowImpl obj, Object value) {
                obj.setDbTableCol((String)value);
            }
        }
        ,
        CreatedAt {
            public Object get(DcmTemplateColumnViewRowImpl obj) {
                return obj.getCreatedAt();
            }

            public void put(DcmTemplateColumnViewRowImpl obj, Object value) {
                obj.setCreatedAt((Date)value);
            }
        }
        ,
        UpdatedAt {
            public Object get(DcmTemplateColumnViewRowImpl obj) {
                return obj.getUpdatedAt();
            }

            public void put(DcmTemplateColumnViewRowImpl obj, Object value) {
                obj.setUpdatedAt((Date)value);
            }
        }
        ,
        UpdatedBy {
            public Object get(DcmTemplateColumnViewRowImpl obj) {
                return obj.getUpdatedBy();
            }

            public void put(DcmTemplateColumnViewRowImpl obj, Object value) {
                obj.setUpdatedBy((String)value);
            }
        }
        ,
        CreatedBy {
            public Object get(DcmTemplateColumnViewRowImpl obj) {
                return obj.getCreatedBy();
            }

            public void put(DcmTemplateColumnViewRowImpl obj, Object value) {
                obj.setCreatedBy((String)value);
            }
        }
        ,
        IsPk {
            public Object get(DcmTemplateColumnViewRowImpl obj) {
                return obj.getIsPk();
            }

            public void put(DcmTemplateColumnViewRowImpl obj, Object value) {
                obj.setIsPk((String)value);
            }
        }
        ,
        Readonly {
            public Object get(DcmTemplateColumnViewRowImpl obj) {
                return obj.getReadonly();
            }

            public void put(DcmTemplateColumnViewRowImpl obj, Object value) {
                obj.setReadonly((String)value);
            }
        }
        ,
        DataType {
            public Object get(DcmTemplateColumnViewRowImpl obj) {
                return obj.getDataType();
            }

            public void put(DcmTemplateColumnViewRowImpl obj, Object value) {
                obj.setDataType((String)value);
            }
        }
        ,
        Visible {
            public Object get(DcmTemplateColumnViewRowImpl obj) {
                return obj.getVisible();
            }

            public void put(DcmTemplateColumnViewRowImpl obj, Object value) {
                obj.setVisible((String)value);
            }
        }
        ,
        Seq {
            public Object get(DcmTemplateColumnViewRowImpl obj) {
                return obj.getSeq();
            }

            public void put(DcmTemplateColumnViewRowImpl obj, Object value) {
                obj.setSeq((Number)value);
            }
        }
        ,
        TemplateId {
            public Object get(DcmTemplateColumnViewRowImpl obj) {
                return obj.getTemplateId();
            }

            public void put(DcmTemplateColumnViewRowImpl obj, Object value) {
                obj.setTemplateId((String)value);
            }
        }
        ,
        ValueSetId {
            public Object get(DcmTemplateColumnViewRowImpl obj) {
                return obj.getValueSetId();
            }

            public void put(DcmTemplateColumnViewRowImpl obj, Object value) {
                obj.setValueSetId((String)value);
            }
        }
        ,
        LKP_YES_NO {
            public Object get(DcmTemplateColumnViewRowImpl obj) {
                return obj.getLKP_YES_NO();
            }

            public void put(DcmTemplateColumnViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LST_USER {
            public Object get(DcmTemplateColumnViewRowImpl obj) {
                return obj.getLST_USER();
            }

            public void put(DcmTemplateColumnViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LKP_LANG {
            public Object get(DcmTemplateColumnViewRowImpl obj) {
                return obj.getLKP_LANG();
            }

            public void put(DcmTemplateColumnViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LKP_DATA_TYPE {
            public Object get(DcmTemplateColumnViewRowImpl obj) {
                return obj.getLKP_DATA_TYPE();
            }

            public void put(DcmTemplateColumnViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LST_VS {
            public Object get(DcmTemplateColumnViewRowImpl obj) {
                return obj.getLST_VS();
            }

            public void put(DcmTemplateColumnViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(DcmTemplateColumnViewRowImpl object);

        public abstract void put(DcmTemplateColumnViewRowImpl object,
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


    public static final int ID = AttributesEnum.Id.index();
    public static final int LOCALE = AttributesEnum.Locale.index();
    public static final int COLUMNLABEL = AttributesEnum.ColumnLabel.index();
    public static final int DBTABLECOL = AttributesEnum.DbTableCol.index();
    public static final int CREATEDAT = AttributesEnum.CreatedAt.index();
    public static final int UPDATEDAT = AttributesEnum.UpdatedAt.index();
    public static final int UPDATEDBY = AttributesEnum.UpdatedBy.index();
    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int ISPK = AttributesEnum.IsPk.index();
    public static final int READONLY = AttributesEnum.Readonly.index();
    public static final int DATATYPE = AttributesEnum.DataType.index();
    public static final int VISIBLE = AttributesEnum.Visible.index();
    public static final int SEQ = AttributesEnum.Seq.index();
    public static final int TEMPLATEID = AttributesEnum.TemplateId.index();
    public static final int VALUESETID = AttributesEnum.ValueSetId.index();
    public static final int LKP_YES_NO = AttributesEnum.LKP_YES_NO.index();
    public static final int LST_USER = AttributesEnum.LST_USER.index();
    public static final int LKP_LANG = AttributesEnum.LKP_LANG.index();
    public static final int LKP_DATA_TYPE = AttributesEnum.LKP_DATA_TYPE.index();
    public static final int LST_VS = AttributesEnum.LST_VS.index();

    /**
     * This is the default constructor (do not remove).
     */
    public DcmTemplateColumnViewRowImpl() {
    }

    /**
     * Gets DcmTemplateColumn entity object.
     * @return the DcmTemplateColumn
     */
    public DcmTemplateColumnImpl getDcmTemplateColumn() {
        return (DcmTemplateColumnImpl)getEntity(0);
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
     * Gets the attribute value for COLUMN_LABEL using the alias name ColumnLabel.
     * @return the COLUMN_LABEL
     */
    public String getColumnLabel() {
        return (String) getAttributeInternal(COLUMNLABEL);
    }

    /**
     * Sets <code>value</code> as attribute value for COLUMN_LABEL using the alias name ColumnLabel.
     * @param value value to set the COLUMN_LABEL
     */
    public void setColumnLabel(String value) {
        setAttributeInternal(COLUMNLABEL, value);
    }

    /**
     * Gets the attribute value for DB_TABLE_COL using the alias name DbTableCol.
     * @return the DB_TABLE_COL
     */
    public String getDbTableCol() {
        return (String) getAttributeInternal(DBTABLECOL);
    }

    /**
     * Sets <code>value</code> as attribute value for DB_TABLE_COL using the alias name DbTableCol.
     * @param value value to set the DB_TABLE_COL
     */
    public void setDbTableCol(String value) {
        setAttributeInternal(DBTABLECOL, value==null ? value : value.toUpperCase());
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
     * Gets the attribute value for IS_PK using the alias name IsPk.
     * @return the IS_PK
     */
    public String getIsPk() {
        return (String) getAttributeInternal(ISPK);
    }

    /**
     * Sets <code>value</code> as attribute value for IS_PK using the alias name IsPk.
     * @param value value to set the IS_PK
     */
    public void setIsPk(String value) {
        setAttributeInternal(ISPK, value);
    }

    /**
     * Gets the attribute value for READONLY using the alias name Readonly.
     * @return the READONLY
     */
    public String getReadonly() {
        return (String) getAttributeInternal(READONLY);
    }

    /**
     * Sets <code>value</code> as attribute value for READONLY using the alias name Readonly.
     * @param value value to set the READONLY
     */
    public void setReadonly(String value) {
        setAttributeInternal(READONLY, value);
    }

    /**
     * Gets the attribute value for DATA_TYPE using the alias name DataType.
     * @return the DATA_TYPE
     */
    public String getDataType() {
        return (String) getAttributeInternal(DATATYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for DATA_TYPE using the alias name DataType.
     * @param value value to set the DATA_TYPE
     */
    public void setDataType(String value) {
        setAttributeInternal(DATATYPE, value);
    }

    /**
     * Gets the attribute value for VISIBLE using the alias name Visible.
     * @return the VISIBLE
     */
    public String getVisible() {
        return (String) getAttributeInternal(VISIBLE);
    }

    /**
     * Sets <code>value</code> as attribute value for VISIBLE using the alias name Visible.
     * @param value value to set the VISIBLE
     */
    public void setVisible(String value) {
        setAttributeInternal(VISIBLE, value);
    }

    /**
     * Gets the attribute value for SEQ using the alias name Seq.
     * @return the SEQ
     */
    public Number getSeq() {
        return (Number) getAttributeInternal(SEQ);
    }

    /**
     * Sets <code>value</code> as attribute value for SEQ using the alias name Seq.
     * @param value value to set the SEQ
     */
    public void setSeq(Number value) {
        setAttributeInternal(SEQ, value);
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
     * Gets the attribute value for VALUE_SET_ID using the alias name ValueSetId.
     * @return the VALUE_SET_ID
     */
    public String getValueSetId() {
        return (String) getAttributeInternal(VALUESETID);
    }

    /**
     * Sets <code>value</code> as attribute value for VALUE_SET_ID using the alias name ValueSetId.
     * @param value value to set the VALUE_SET_ID
     */
    public void setValueSetId(String value) {
        setAttributeInternal(VALUESETID, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LKP_YES_NO.
     */
    public RowSet getLKP_YES_NO() {
        return (RowSet)getAttributeInternal(LKP_YES_NO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LST_USER.
     */
    public RowSet getLST_USER() {
        return (RowSet)getAttributeInternal(LST_USER);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LKP_LANG.
     */
    public RowSet getLKP_LANG() {
        return (RowSet)getAttributeInternal(LKP_LANG);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LKP_DATA_TYPE.
     */
    public RowSet getLKP_DATA_TYPE() {
        return (RowSet)getAttributeInternal(LKP_DATA_TYPE);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LST_VS.
     */
    public RowSet getLST_VS() {
        return (RowSet)getAttributeInternal(LST_VS);
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

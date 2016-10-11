package team.epm.dms.view;

import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;

import team.epm.dms.model.DmsValueSetImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Jan 07 15:57:23 CST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DmsValueSetViewRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Id {
            public Object get(DmsValueSetViewRowImpl obj) {
                return obj.getId();
            }

            public void put(DmsValueSetViewRowImpl obj, Object value) {
                obj.setId((String)value);
            }
        }
        ,
        Locale {
            public Object get(DmsValueSetViewRowImpl obj) {
                return obj.getLocale();
            }

            public void put(DmsValueSetViewRowImpl obj, Object value) {
                obj.setLocale((String)value);
            }
        }
        ,
        Name {
            public Object get(DmsValueSetViewRowImpl obj) {
                return obj.getName();
            }

            public void put(DmsValueSetViewRowImpl obj, Object value) {
                obj.setName((String)value);
            }
        }
        ,
        Code {
            public Object get(DmsValueSetViewRowImpl obj) {
                return obj.getCode();
            }

            public void put(DmsValueSetViewRowImpl obj, Object value) {
                obj.setCode((String)value);
            }
        }
        ,
        Source {
            public Object get(DmsValueSetViewRowImpl obj) {
                return obj.getSource();
            }

            public void put(DmsValueSetViewRowImpl obj, Object value) {
                obj.setSource((String)value);
            }
        }
        ,
        CreatedAt {
            public Object get(DmsValueSetViewRowImpl obj) {
                return obj.getCreatedAt();
            }

            public void put(DmsValueSetViewRowImpl obj, Object value) {
                obj.setCreatedAt((Date)value);
            }
        }
        ,
        UpdatedAt {
            public Object get(DmsValueSetViewRowImpl obj) {
                return obj.getUpdatedAt();
            }

            public void put(DmsValueSetViewRowImpl obj, Object value) {
                obj.setUpdatedAt((Date)value);
            }
        }
        ,
        UpdatedBy {
            public Object get(DmsValueSetViewRowImpl obj) {
                return obj.getUpdatedBy();
            }

            public void put(DmsValueSetViewRowImpl obj, Object value) {
                obj.setUpdatedBy((String)value);
            }
        }
        ,
        CreatedBy {
            public Object get(DmsValueSetViewRowImpl obj) {
                return obj.getCreatedBy();
            }

            public void put(DmsValueSetViewRowImpl obj, Object value) {
                obj.setCreatedBy((String)value);
            }
        }
        ,
        ValueSetId {
            public Object get(DmsValueSetViewRowImpl obj) {
                return obj.getValueSetId();
            }

            public void put(DmsValueSetViewRowImpl obj, Object value) {
                obj.setValueSetId((String)value);
            }
        }
        ,
        DisplayLength {
            public Object get(DmsValueSetViewRowImpl obj) {
                return obj.getDisplayLength();
            }

            public void put(DmsValueSetViewRowImpl obj, Object value) {
                obj.setDisplayLength((Number)value);
            }
        }
        ,
        DmsGroupValueView {
            public Object get(DmsValueSetViewRowImpl obj) {
                return obj.getDmsGroupValueView();
            }

            public void put(DmsValueSetViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LKP_LANG {
            public Object get(DmsValueSetViewRowImpl obj) {
                return obj.getLKP_LANG();
            }

            public void put(DmsValueSetViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LSV_USER {
            public Object get(DmsValueSetViewRowImpl obj) {
                return obj.getLSV_USER();
            }

            public void put(DmsValueSetViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(DmsValueSetViewRowImpl object);

        public abstract void put(DmsValueSetViewRowImpl object, Object value);

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
    public static final int NAME = AttributesEnum.Name.index();
    public static final int CODE = AttributesEnum.Code.index();
    public static final int SOURCE = AttributesEnum.Source.index();
    public static final int CREATEDAT = AttributesEnum.CreatedAt.index();
    public static final int UPDATEDAT = AttributesEnum.UpdatedAt.index();
    public static final int UPDATEDBY = AttributesEnum.UpdatedBy.index();
    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int VALUESETID = AttributesEnum.ValueSetId.index();
    public static final int DISPLAYLENGTH = AttributesEnum.DisplayLength.index();
    public static final int DMSGROUPVALUEVIEW = AttributesEnum.DmsGroupValueView.index();
    public static final int LKP_LANG = AttributesEnum.LKP_LANG.index();
    public static final int LSV_USER = AttributesEnum.LSV_USER.index();

    /**
     * This is the default constructor (do not remove).
     */
    public DmsValueSetViewRowImpl() {
    }

    /**
     * Gets DmsValueSet entity object.
     * @return the DmsValueSet
     */
    public DmsValueSetImpl getDmsValueSet() {
        return (DmsValueSetImpl)getEntity(0);
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
     * Gets the attribute value for NAME using the alias name Name.
     * @return the NAME
     */
    public String getName() {
        return (String) getAttributeInternal(NAME);
    }

    /**
     * Sets <code>value</code> as attribute value for NAME using the alias name Name.
     * @param value value to set the NAME
     */
    public void setName(String value) {
        setAttributeInternal(NAME, value);
    }

    /**
     * Gets the attribute value for CODE using the alias name Code.
     * @return the CODE
     */
    public String getCode() {
        return (String) getAttributeInternal(CODE);
    }

    /**
     * Sets <code>value</code> as attribute value for CODE using the alias name Code.
     * @param value value to set the CODE
     */
    public void setCode(String value) {
        setAttributeInternal(CODE, value);
    }

    /**
     * Gets the attribute value for SOURCE using the alias name Source.
     * @return the SOURCE
     */
    public String getSource() {
        return (String) getAttributeInternal(SOURCE);
    }

    /**
     * Sets <code>value</code> as attribute value for SOURCE using the alias name Source.
     * @param value value to set the SOURCE
     */
    public void setSource(String value) {
        setAttributeInternal(SOURCE, value);
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
     * Gets the attribute value for the calculated attribute ValueSetId.
     * @return the ValueSetId
     */
    public String getValueSetId() {
        return (String) getAttributeInternal(VALUESETID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ValueSetId.
     * @param value value to set the  ValueSetId
     */
    public void setValueSetId(String value) {
        setAttributeInternal(VALUESETID, value);
    }


    /**
     * Gets the attribute value for DISPLAY_LENGTH using the alias name DisplayLength.
     * @return the DISPLAY_LENGTH
     */
    public Number getDisplayLength() {
        return (Number) getAttributeInternal(DISPLAYLENGTH);
    }

    /**
     * Sets <code>value</code> as attribute value for DISPLAY_LENGTH using the alias name DisplayLength.
     * @param value value to set the DISPLAY_LENGTH
     */
    public void setDisplayLength(Number value) {
        setAttributeInternal(DISPLAYLENGTH, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link DmsGroupValueView.
     */
    public RowIterator getDmsGroupValueView() {
        return (RowIterator)getAttributeInternal(DMSGROUPVALUEVIEW);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LKP_LANG.
     */
    public RowSet getLKP_LANG() {
        return (RowSet)getAttributeInternal(LKP_LANG);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LSV_USER.
     */
    public RowSet getLSV_USER() {
        return (RowSet)getAttributeInternal(LSV_USER);
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

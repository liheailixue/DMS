package team.epm.odi11g.view;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Jan 28 15:26:25 CST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class Odi11SceneParamExViewRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        SceneId {
            public Object get(Odi11SceneParamExViewRowImpl obj) {
                return obj.getSceneId();
            }

            public void put(Odi11SceneParamExViewRowImpl obj, Object value) {
                obj.setSceneId((String)value);
            }
        }
        ,
        PAlias {
            public Object get(Odi11SceneParamExViewRowImpl obj) {
                return obj.getPAlias();
            }

            public void put(Odi11SceneParamExViewRowImpl obj, Object value) {
                obj.setPAlias((String)value);
            }
        }
        ,
        PName {
            public Object get(Odi11SceneParamExViewRowImpl obj) {
                return obj.getPName();
            }

            public void put(Odi11SceneParamExViewRowImpl obj, Object value) {
                obj.setPName((String)value);
            }
        }
        ,
        ValueSetId {
            public Object get(Odi11SceneParamExViewRowImpl obj) {
                return obj.getValueSetId();
            }

            public void put(Odi11SceneParamExViewRowImpl obj, Object value) {
                obj.setValueSetId((String)value);
            }
        }
        ,
        value {
            public Object get(Odi11SceneParamExViewRowImpl obj) {
                return obj.getvalue();
            }

            public void put(Odi11SceneParamExViewRowImpl obj, Object value) {
                obj.setvalue((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(Odi11SceneParamExViewRowImpl object);

        public abstract void put(Odi11SceneParamExViewRowImpl object,
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

    public static final int SCENEID = AttributesEnum.SceneId.index();
    public static final int PALIAS = AttributesEnum.PAlias.index();
    public static final int PNAME = AttributesEnum.PName.index();
    public static final int VALUESETID = AttributesEnum.ValueSetId.index();
    public static final int VALUE = AttributesEnum.value.index();

    /**
     * This is the default constructor (do not remove).
     */
    public Odi11SceneParamExViewRowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute SceneId.
     * @return the SceneId
     */
    public String getSceneId() {
        return (String) getAttributeInternal(SCENEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SceneId.
     * @param value value to set the  SceneId
     */
    public void setSceneId(String value) {
        setAttributeInternal(SCENEID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute PAlias.
     * @return the PAlias
     */
    public String getPAlias() {
        return (String) getAttributeInternal(PALIAS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute PAlias.
     * @param value value to set the  PAlias
     */
    public void setPAlias(String value) {
        setAttributeInternal(PALIAS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute PName.
     * @return the PName
     */
    public String getPName() {
        return (String) getAttributeInternal(PNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute PName.
     * @param value value to set the  PName
     */
    public void setPName(String value) {
        setAttributeInternal(PNAME, value);
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
     * Gets the attribute value for the calculated attribute value.
     * @return the value
     */
    public String getvalue() {
        return (String) getAttributeInternal(VALUE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute value.
     * @param value value to set the  value
     */
    public void setvalue(String value) {
        setAttributeInternal(VALUE, value);
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

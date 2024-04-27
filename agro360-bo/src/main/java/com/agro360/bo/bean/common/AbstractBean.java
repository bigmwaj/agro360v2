package com.agro360.bo.bean.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.common.ClientOperationEnumVd;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AbstractBean implements Serializable {

	private static final long serialVersionUID = -8531682957497502966L;

	private final String __TYPE__ = "BEAN";

	private boolean valueChanged;

	private boolean visible = true;

	private boolean editable = true;

	private String label;

	private FieldMetadata<String> deleteBtn = new FieldMetadata<>();

	private FieldMetadata<String> saveBtn = new FieldMetadata<>("Créer ligne");

	@JsonIgnore
	private AbstractBean ownerBean;

	@JsonIgnore
	private AbstractBean rootBean;

	@NonNull
	private ClientOperationEnumVd action = ClientOperationEnumVd.SYNC;

	public static final Consumer<AbstractBean> setActionToCreate = b -> b.setAction(ClientOperationEnumVd.CREATE);

	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}

	private String getGetterName(String fieldName) {
		return "get" + ((fieldName.charAt(0) + "").toUpperCase()) + fieldName.substring(1);
	}

	@SuppressWarnings("unchecked")
	public <T> FieldMetadata<T> getField(String fieldName) {
		try {
			return (FieldMetadata<T>) getClass().getMethod(getGetterName(fieldName)).invoke(this);
		} catch (Exception e) {
			var msgTpl = "L'attribut %s n'existe pas";
			getLogger().error(String.format(msgTpl, fieldName), e);
			throw new RuntimeException(String.format(msgTpl, fieldName));
		}
	}

	public AbstractBean getBean(String fieldName) {
		try {
			return (AbstractBean) getClass().getMethod(getGetterName(fieldName)).invoke(this);
		} catch (Exception e) {
			var msgTpl = "L'attribut %s n'existe pas";
			getLogger().error(String.format(msgTpl, fieldName), e);
			throw new RuntimeException(String.format(msgTpl, fieldName));
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<AbstractBean> getBeans(String fieldName) {
		try {
			return (Collection<AbstractBean>) getClass().getMethod(getGetterName(fieldName)).invoke(this);
		} catch (Exception e) {
			var msgTpl = "L'attribut %s n'existe pas";
			getLogger().error(String.format(msgTpl, fieldName), e);
			throw new RuntimeException(String.format(msgTpl, fieldName));
		}
	}
}

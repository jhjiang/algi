package com.codejstudio.lim.pojo.statement;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.codejstudio.lim.common.exception.LIMException;
import com.codejstudio.lim.common.util.CollectionUtil;
import com.codejstudio.lim.pojo.BaseElement;
import com.codejstudio.lim.pojo.GenericActionableElement;
import com.codejstudio.lim.pojo.GenericElement;
import com.codejstudio.lim.pojo.i.IConvertible;
import com.codejstudio.lim.pojo.i.IGroupable;
import com.codejstudio.lim.pojo.util.ElementTrace;
import com.codejstudio.lim.pojo.util.GroupableUtil;
import com.codejstudio.lim.pojo.util.PojoElementClassConstant;

/**
 * StatementGroup.class
 * 
 * @author <ul><li>Jeffrey Jiang</li></ul>
 * @see     
 * @since   lim4j_v1.0.0
 */
public class StatementGroup extends Statement implements IGroupable<Statement> {

	/* constants */

	private static final long serialVersionUID = 7497481948811381081L;


	/* variables: arrays, collections, maps, groups */

	private List<BaseElement> innerGroupList;

	private Map<String, BaseElement> innerGroupMap;


	/* constructors */

	/**
	 * only for JAXB auto unmarshalling usage
	 */
	public StatementGroup() {
		super();
	}

	public StatementGroup(boolean initIdFlag) throws LIMException {
		super(initIdFlag, true);
	}

	public StatementGroup(boolean initIdFlag, Statement... statements) throws LIMException {
		super(initIdFlag, true);
		addGroupElement(statements);
	}


	public StatementGroup(Statement... statements) throws LIMException {
		this(true, statements);
	}


	/* initializers */

	/**
	 * only for com.codejstudio.lim.common.util.InitializationUtil#autoInit() usage
	 */
	static void autoInit() {}

	static {
		PojoElementClassConstant.registerElementClassForInit(StatementGroup.class);
		GroupableUtil.registerGroupableClassForInit(Statement.class, StatementGroup.class);
		Statement.registerSubPojoClassForInit(StatementGroup.class);
	}


	private void initInnerGroupList() throws LIMException {
		if (this.innerGroupList == null) {
			this.innerGroupList = CollectionUtil.generateNewList();
		}
	}

	private void initInnerGroupMap() throws LIMException {
		if (this.innerGroupMap == null) {
			this.innerGroupMap = CollectionUtil.generateNewMap();
		}
	}


	/* destroyers */

	private void destroyInnerGroupList() {
		if (this.innerGroupList != null && this.innerGroupList.size() == 0) {
			this.innerGroupList = null;
		}
	}

	private void destroyInnerGroupMap() {
		if (this.innerGroupMap != null && this.innerGroupMap.size() == 0) {
			this.innerGroupMap = null;
		}
	}


	/* getters & setters */

	@Override
	public List<Statement> getInnerGroupList() throws LIMException {
		if (CollectionUtil.checkNullOrEmpty(this.innerGroupList)) {
			return null;
		}

		List<Statement> l = CollectionUtil.generateNewList();
		for (BaseElement be : this.innerGroupList) {
			l.add((Statement) super.getInnerElementDelegate(be));
		}
		return CollectionUtil.checkNullOrEmpty(l) ? null : l;
	}


	/* CRUD for arrays, collections, maps, groups: group elements */

	@Override
	public int size() {
		return (this.innerGroupList == null) ? 0 : this.innerGroupList.size();
	}

	@Override
	public boolean containGroupElement(final Statement groupElement) {
		return (groupElement == null) ? false : containGroupElement(groupElement.getId());
	}

	@Override
	public boolean containGroupElement(final String id) {
		return (id == null || this.innerGroupMap == null) ? false : this.innerGroupMap.containsKey(id);
	}

	@Override
	public boolean addGroupElement(final Statement... groupElements) throws LIMException {
		return addGroupElement((groupElements == null) ? null : Arrays.asList(groupElements));
	}

	@Override
	public boolean addGroupElement(final Collection<? extends Statement> groupElementCollection) 
			throws LIMException {
		if (CollectionUtil.checkNullOrEmpty(groupElementCollection)) {
			return false;
		}

		try {
			initInnerGroupList();
			initInnerGroupMap();
			boolean flag = true;
			for (Statement e : groupElementCollection) {
				if (e == null || e.getId() == null) {
					continue;
				}
				if (this.innerGroupMap.containsKey(e.getId())) {
					flag = false;
					continue;
				}

				BaseElement be = new BaseElement(e);
				this.innerGroupMap.put(e.getId(), be);
				flag &= this.innerGroupList.add(be) 
						& super.addInnerElementDelegate(e) 
						& super.putIntegratedElementDelegate(
								IGroupable.GROUP_KEY_PREFIX_IEM + e.getId(), be);
			}

			if (flag) {
				ElementTrace.log.info(this.toBaseString() + ": addGroupElement(" 
						+ BaseElement.toBaseString(groupElementCollection) + ")");
			} else {
				ElementTrace.log.warn(this.toBaseString() + "fail to addGroupElement(" 
						+ BaseElement.toBaseString(groupElementCollection) + ")");
			}
			return flag;
		} finally {
			destroyInnerGroupList();
			destroyInnerGroupMap();
		}
	}

	@Override
	public boolean removeGroupElement(final String id) {
		if (id == null || !this.innerGroupMap.containsKey(id)) {
			ElementTrace.log.warn(this.toBaseString() + "fail to removeGroupElement(" + id + ")");
			return false;
		}

		try {
			boolean flag = super.removeIntegratedElementDelegate(IGroupable.GROUP_KEY_PREFIX_IEM + id) 
					& super.removeInnerElementDelegate(id) 
					& this.innerGroupList.remove(this.innerGroupMap.remove(id));

			if (flag) {
				ElementTrace.log.info(this.toBaseString() + ": removeGroupElement(" + id + ")");
			} else {
				ElementTrace.log.warn(this.toBaseString() + "fail to removeGroupElement(" + id + ")");
			}
			return flag;
		} finally {
			destroyInnerGroupList();
			destroyInnerGroupMap();
		}
	}

	@Override
	public boolean replaceGroupElement(final BaseElement replacee, final Statement replacer) 
			throws LIMException {
		return (Objects.equals(replacee, replacer) || (replacee != null && !(replacee instanceof Statement) 
						&& !replacee.getClass().equals(BaseElement.class))) ? false 
				: (removeGroupElement((replacee == null) ? null : replacee.getId()) 
						& addGroupElement((replacer == null) ? null : Arrays.asList(replacer)));
	}


	/* overridden methods */

	@Override
	public IConvertible reload(final IConvertible convertible, 
			final Map<String, GenericElement> rootElementMap, 
			final Map<String, GenericActionableElement> rootActionableElementMap) throws LIMException {
		if (super.reload(convertible, rootElementMap, rootActionableElementMap) == null) {
			return null;
		}
		reloadFromRootElementMap(rootElementMap);
		return (IConvertible) this;
	}

	private void reloadFromRootElementMap(final Map<String, GenericElement> rootElementMap) 
			throws LIMException {
		Map<String, BaseElement> item;
		Collection<BaseElement> vc;
		if (CollectionUtil.checkNullOrEmpty(rootElementMap) 
				|| (item = super.getIntegratedElementMap()) == null 
				|| CollectionUtil.checkNullOrEmpty(vc = item.values())) {
			return;
		}

		initInnerGroupList();
		initInnerGroupMap();
		for (BaseElement be : vc) {
			GenericElement ge;
			if (be == null || be.getId() == null 
					|| !((ge = rootElementMap.get(be.getId())) instanceof Statement)) {
				continue;
			}
			super.addInnerElementDelegate(be, ge);
			this.innerGroupList.add(be);
			this.innerGroupMap.put(be.getId(), be);
		}
		destroyInnerGroupList();
		destroyInnerGroupMap();
	}


	@Override
	public StatementGroup cloneElement(final Map<String, BaseElement> clonedElementMap) throws LIMException {
		if (!CollectionUtil.checkNullOrEmpty(clonedElementMap) && this.id != null) {
			if (clonedElementMap.containsKey(this.id)) {
				BaseElement value = clonedElementMap.get(this.id);
				if (value != null && value.getClass().equals(StatementGroup.class)) {
					return (StatementGroup) value;
				}
			} else {
				clonedElementMap.put(this.id, new BaseElement(this.id, this.type));
			}
		}

		StatementGroup clonedElement = (StatementGroup) super.cloneElement(clonedElementMap);
		return cloneToElement(clonedElement, clonedElementMap);
	}

	@Override
	public StatementGroup cloneToElement(final GenericElement clonedElement) throws LIMException {
		GenericElement ce;
		return (!(clonedElement instanceof StatementGroup) 
						|| !((ce = super.cloneToElement(clonedElement)) instanceof StatementGroup)) 
				? null : cloneToElement((StatementGroup) ce, null);
	}

	private StatementGroup cloneToElement(final StatementGroup clonedElement, 
			final Map<String, BaseElement> clonedElementMap) throws LIMException {
		if (!CollectionUtil.checkNullOrEmpty(this.innerGroupMap)) {
			clonedElement.initInnerGroupList();
			clonedElement.initInnerGroupMap();
			Set<String> igks = this.innerGroupMap.keySet();
			for (String id : igks) {
				BaseElement be = this.innerGroupMap.get(id);
				if (be == null) {
					continue;
				}
				BaseElement clonedBe = be.cloneElement(clonedElementMap);
				clonedElement.innerGroupList.add(clonedBe);
				clonedElement.innerGroupMap.put(id, clonedBe);
			}
			clonedElement.destroyInnerGroupList();
			clonedElement.destroyInnerGroupMap();
		}

		return clonedElement;
	}

}
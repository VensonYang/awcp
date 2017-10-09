package org.szcloud.framework.core.domain;

import org.szcloud.framework.core.domain.internal.AndCriterion;
import org.szcloud.framework.core.domain.internal.BetweenCriterion;
import org.szcloud.framework.core.domain.internal.ContainsTextCriterion;
import org.szcloud.framework.core.domain.internal.EqCriterion;
import org.szcloud.framework.core.domain.internal.EqPropCriterion;
import org.szcloud.framework.core.domain.internal.GeCriterion;
import org.szcloud.framework.core.domain.internal.GePropCriterion;
import org.szcloud.framework.core.domain.internal.GtCriterion;
import org.szcloud.framework.core.domain.internal.GtPropCriterion;
import org.szcloud.framework.core.domain.internal.InCriterion;
import org.szcloud.framework.core.domain.internal.IsEmptyCriterion;
import org.szcloud.framework.core.domain.internal.IsNullCriterion;
import org.szcloud.framework.core.domain.internal.LeCriterion;
import org.szcloud.framework.core.domain.internal.LePropCriterion;
import org.szcloud.framework.core.domain.internal.LtCriterion;
import org.szcloud.framework.core.domain.internal.LtPropCriterion;
import org.szcloud.framework.core.domain.internal.NotCriterion;
import org.szcloud.framework.core.domain.internal.NotEmptyCriterion;
import org.szcloud.framework.core.domain.internal.NotEqCriterion;
import org.szcloud.framework.core.domain.internal.NotEqPropCriterion;
import org.szcloud.framework.core.domain.internal.NotInCriterion;
import org.szcloud.framework.core.domain.internal.NotNullCriterion;
import org.szcloud.framework.core.domain.internal.OrCriterion;
import org.szcloud.framework.core.domain.internal.SizeEqCriterion;
import org.szcloud.framework.core.domain.internal.SizeGeCriterion;
import org.szcloud.framework.core.domain.internal.SizeGtCriterion;
import org.szcloud.framework.core.domain.internal.SizeLeCriterion;
import org.szcloud.framework.core.domain.internal.SizeLtCriterion;
import org.szcloud.framework.core.domain.internal.SizeNotEqCriterion;
import org.szcloud.framework.core.domain.internal.StartsWithTextCriterion;
import java.util.Collection;

public class Criterions{
	public static QueryCriterion eq(String propName, Object value) {
		return new EqCriterion(propName, value);
	}

	public static QueryCriterion notEq(String propName, Object value) {
		return new NotEqCriterion(propName, value);
	}

	public static QueryCriterion ge(String propName, Comparable<?> value) {
		return new GeCriterion(propName, value);
	}

	public static QueryCriterion gt(String propName, Comparable<?> value) {
		return new GtCriterion(propName, value);
	}

	public static QueryCriterion le(String propName, Comparable<?> value) {
		return new LeCriterion(propName, value);
	}

	public static QueryCriterion lt(String propName, Comparable<?> value) {
		return new LtCriterion(propName, value);
	}

	public static QueryCriterion eqProp(String propName1, String propName2) {
		return new EqPropCriterion(propName1, propName2);
	}

	public static QueryCriterion notEqProp(String propName1, String propName2) {
		return new NotEqPropCriterion(propName1, propName2);
	}

	public static QueryCriterion gtProp(String propName1, String propName2) {
		return new GtPropCriterion(propName1, propName2);
	}

	public static QueryCriterion geProp(String propName1, String propName2) {
		return new GePropCriterion(propName1, propName2);
	}

	public static QueryCriterion ltProp(String propName1, String propName2) {
		return new LtPropCriterion(propName1, propName2);
	}

	public static QueryCriterion leProp(String propName1, String propName2) {
		return new LePropCriterion(propName1, propName2);
	}

	public static QueryCriterion sizeEq(String propName, int size) {
		return new SizeEqCriterion(propName, size);
	}

	public static QueryCriterion sizeNotEq(String propName, int size) {
		return new SizeNotEqCriterion(propName, size);
	}

	public static QueryCriterion sizeGt(String propName, int size) {
		return new SizeGtCriterion(propName, size);
	}

	public static QueryCriterion sizeGe(String propName, int size) {
		return new SizeGeCriterion(propName, size);
	}

	public static QueryCriterion sizeLt(String propName, int size) {
		return new SizeLtCriterion(propName, size);
	}

	public static QueryCriterion sizeLe(String propName, int size) {
		return new SizeLeCriterion(propName, size);
	}

	public static QueryCriterion containsText(String propName, String value) {
		return new ContainsTextCriterion(propName, value);
	}

	public static QueryCriterion startsWithText(String propName, String value) {
		return new StartsWithTextCriterion(propName, value);
	}

	public static QueryCriterion in(String propName, Collection<?> value) {
		return new InCriterion(propName, value);
	}

	public static QueryCriterion in(String propName, Object[] value) {
		return new InCriterion(propName, value);
	}

	public static QueryCriterion notIn(String propName, Collection<?> value) {
		return new NotInCriterion(propName, value);
	}

	public static QueryCriterion notIn(String propName, Object[] value) {
		return new NotInCriterion(propName, value);
	}

	public static QueryCriterion between(String propName, Comparable<?> from, Comparable<?> to) {
		return new BetweenCriterion(propName, from, to);
	}

	public static QueryCriterion isNull(String propName) {
		return new IsNullCriterion(propName);
	}

	public static QueryCriterion notNull(String propName) {
		return new NotNullCriterion(propName);
	}

	public static QueryCriterion isEmpty(String propName) {
		return new IsEmptyCriterion(propName);
	}

	public static QueryCriterion notEmpty(String propName) {
		return new NotEmptyCriterion(propName);
	}

	public static QueryCriterion not(QueryCriterion criterion) {
		return new NotCriterion(criterion);
	}

	public static QueryCriterion and(QueryCriterion[] criterions) {
		return new AndCriterion(criterions);
	}

	public static QueryCriterion or(QueryCriterion[] criterions) {
		return new OrCriterion(criterions);
	}
}


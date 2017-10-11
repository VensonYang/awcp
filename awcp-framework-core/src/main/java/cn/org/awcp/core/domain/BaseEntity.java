/**
 * BaseEntity 所有领域模型的基类
 * @author caoyong
 *
 */
package cn.org.awcp.core.domain;

import java.io.Serializable;
import java.util.List;

import cn.org.awcp.core.utils.Springfactory;

/**
 * 抽象实体类，可作为所有领域实体的基类。
 *
 *
 */

public abstract class BaseEntity implements Entity {

	private static EntityRepository repository;
	
	private Long id;
    
	public  static EntityRepository getRepository() {
    	if(repository==null)
    		repository = Springfactory.getBean("repository");
		return BaseEntity.repository;
	}
    
    /**
     * 设置仓储
     * @param repository
     */
    public static void setRepository(EntityRepository repository) {
    	BaseEntity.repository = repository;
    }

	private static final long serialVersionUID = 8882145540383345037L;

	public void save() {
		getRepository().save(this);
	}

	public void remove() {
		getRepository().remove(this);
	}
	
	public static <T extends Entity> T get(Class<T> clazz, Serializable id) {
		return (T) getRepository().get(clazz, id);
	}

	public static <T extends Entity> T getUnmodified(Class<T> clazz, T entity) {
		return (T) getRepository().getUnmodified(clazz, entity);
	}

	public static <T extends Entity> T load(Class<T> clazz, Serializable id) {
		return (T) getRepository().load(clazz, id);
	}

	public static <T extends Entity> List<T> findAll(Class<T> clazz) {
		return getRepository().findAll(clazz);
	}
	public static <T extends Entity> List<T> selectByExample(Class<T> clazz,BaseExample example) {
		return getRepository().selectByExample(clazz, example);
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}

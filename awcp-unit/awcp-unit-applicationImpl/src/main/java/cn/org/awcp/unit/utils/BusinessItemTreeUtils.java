package cn.org.awcp.unit.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import cn.org.awcp.unit.vo.BusinessItemTreeNode;
import cn.org.awcp.unit.vo.EnvSystemBusinessItemVO;

/**
* @ClassName: ResourceTreeUtils 
* @Description: 资源树的操作工具工具
 * @author Bobo
 *
 */
public class BusinessItemTreeUtils {
	/*
	 * 存储需要遍历的资源结点；
	 */
	private HashMap<String, EnvSystemBusinessItemVO> resourceNodes ;
	
	/*
	 * 存储已遍历的资源结点；
	 */
	private HashMap<String, String> processedNodes;	
	
	/**
	 * 初始化资源结点和已遍历的资源结点
	 * @param resources:需要生产资源树的结点集合；
	 */
	private void initialize(List<EnvSystemBusinessItemVO> resources){
		resourceNodes = new HashMap<String, EnvSystemBusinessItemVO>();
		processedNodes = new HashMap<String, String>();
		for(int i = 0; i < resources.size(); i++){
			EnvSystemBusinessItemVO temp  = resources.get(i);
			if(temp.getParentId() == null){
				temp.setParentId("");
			}
			resourceNodes.put(temp.getItemId().toString(), temp);
		}
	}
	
	/**
	 * 根据资源结点结合，生成多棵资源树，并返回树集合；
	 * @param resources
	 * @return 资源树数组集合；
	 */
	public List<EnvSystemBusinessItemVO[]> generateTreeView(List<EnvSystemBusinessItemVO> resources){
		initialize(resources);
		List<EnvSystemBusinessItemVO[]> forest = new ArrayList<EnvSystemBusinessItemVO[]>();
		List<EnvSystemBusinessItemVO> roots = getRoots();
		for(int i = 0; i < roots.size();i++){
			EnvSystemBusinessItemVO root = roots.get(i);
			String keysString = generateNodeIdString(root);
			EnvSystemBusinessItemVO[] tree = getPunResourceArrayByKeys(keysString);
			if(tree != null){
				forest.add(tree);
			}
		}		
		return forest;
	}
	
	/**
	 * 根据某一资源结点，找到其所在那棵树的遍历结点Id列表，例如1;2;4;3等；
	 * @param resource
	 * @return 返回结点ID列表，用”;"分隔并以“;"结尾;
	 */
	private String generateNodeIdString(EnvSystemBusinessItemVO resource){
		EnvSystemBusinessItemVO parent = getParent(resource);
        List<EnvSystemBusinessItemVO> children = getChildren(resource);
        if (parent != null && !processedNodes.containsKey(parent.getItemId().toString()))
        {
            return generateNodeIdString(parent);
        }
        if (children.size() == 0)
        {
            processedNodes.put(resource.getItemId().toString(), resource.getItemId().toString());
            return resource.getItemId().toString() + ";";
        }
        StringBuilder sb = new StringBuilder();
        if (children.size() > 0)
        {
            processedNodes.put(resource.getItemId().toString(), resource.getItemId().toString());
            sb.append(resource.getItemId().toString() + ";");
            for (int i = 0; i < children.size(); i++)
            {
            	EnvSystemBusinessItemVO temp = children.get(i);
                sb.append(generateNodeIdString(temp));
            } 
        }
        return sb.toString();
	}
	
	/**
	 * 在集合中查找所有的树根结点；
	 * @return 树根结点集合;
	 */
	private List<EnvSystemBusinessItemVO> getRoots(){		
		List<EnvSystemBusinessItemVO> roots = new ArrayList<EnvSystemBusinessItemVO>();
		for(EnvSystemBusinessItemVO source:resourceNodes.values()){
			if(source.getParentId() == null || !resourceNodes.containsKey(source.getParentId().toString())){
				roots.add(source);
			}
		}
		ComparatorPunResource comparator=new ComparatorPunResource();
		Collections.sort(roots, comparator);
        return roots;
	}
	
	/**
	 * 在集合中查某一结点的子节点并安装sequence排序;
	 * @param resource
	 * @return 有序的子节点集合;
	 */
	private List<EnvSystemBusinessItemVO> getChildren(EnvSystemBusinessItemVO resource){		
		List<EnvSystemBusinessItemVO> children = new ArrayList<EnvSystemBusinessItemVO>();
		for(EnvSystemBusinessItemVO temp:resourceNodes.values()){
			if(temp.getParentId() != null && temp.getParentId().compareTo(resource.getItemId()) == 0){
				children.add(temp);
			}
		}
		ComparatorPunResource comparator=new ComparatorPunResource();
		Collections.sort(children, comparator);
        return children;
	}
	
	/**
	 * 在集合中查找某一结点的父节点
	 * @param resource
	 * @return 父节点
	 */
	private EnvSystemBusinessItemVO getParent(EnvSystemBusinessItemVO resource){
		if(resource.getParentId() == null)
			return null;
		if(resourceNodes.containsKey(resource.getParentId().toString())){
			return (EnvSystemBusinessItemVO) resourceNodes.get(resource.getParentId().toString()); 
		}
        return null;
	}
	
	/**
	 * 实现PunResource排序的类；
	 * @author Bobo
	 *
	 */
	public class ComparatorPunResource implements Comparator<EnvSystemBusinessItemVO>{
		/**
		 * 比较两个PunResource的大小;
		 */
		 public int compare(EnvSystemBusinessItemVO a, EnvSystemBusinessItemVO b) {
			 if(a.getSequence()== null)
				 return -1;
			 if(b.getSequence() == null)
				 return 1;
		  return a.getSequence().compareTo(b.getSequence());		 
		}
	}
	
	/**
	 * 根据ID列表提取有序的资源结点数组;
	 * @param keysString
	 * @return 有序的资源结点数组;
	 */
	private EnvSystemBusinessItemVO[] getPunResourceArrayByKeys(String keysString){
		if(keysString == null)
			return null;
		if(keysString.endsWith(";")){
			keysString = keysString.substring(0, keysString.length()-1);
		}
		if(keysString.length() == 0)
			return null;
		String[] keys = keysString.split(";");
		EnvSystemBusinessItemVO[] rtnAry = new EnvSystemBusinessItemVO[keys.length];
		for(int i = 0 ; i < keys.length; i++){
			rtnAry[i] = (EnvSystemBusinessItemVO) resourceNodes.get(keys[i]);
		}
		return rtnAry;
	}
	
	public static List<BusinessItemTreeNode> getPlainZNodes(EnvSystemBusinessItemVO[] resource) {
		List<BusinessItemTreeNode> zNodes = new ArrayList<BusinessItemTreeNode>();
		for (EnvSystemBusinessItemVO eachRes : resource) {
			BusinessItemTreeNode zNode = new BusinessItemTreeNode();
			zNode.setId(eachRes.getItemId());
			zNode.setpId(eachRes.getParentId());
			zNode.setParentId(eachRes.getParentId());
			String name = eachRes.getName();
			if(eachRes.getYear() != null){
				name = name + "(" + eachRes.getYear().toString() + ")";
			}
			zNode.setName(name);
			zNode.setChecked(false);
			zNode.setComment(eachRes.getComment());
			zNode.setOpen(false);
			zNodes.add(zNode);
		}
		
		return zNodes;
	}
	
	
	public static String getLastPId(String PId) {
		return PId.split(",")[PId.split(",").length-1];
	}
	
}

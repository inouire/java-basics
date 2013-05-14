package inouire.basics.myml;

import java.util.ArrayList;

/**
 * The object used for MyMl tree structure
 * @author Edouard Garnier de Labareyre
 */
public class Element {
    
    private String key;
    private String value;
    private ArrayList<Element> subentries;
    
    private Element parent=null;
    private int indent=0;
    
    /**
     * Constructor for a node (because it has no value)
     * @param key the key of the node to create
     */
    public Element(String key){
        this.key = key;
        this.value = null;
        this.subentries = new ArrayList<Element>();
    }
    
    /**
     * Constructor for a leaf (key+value)
     * If value is null, a node will be created
     * @param key the key of the leaf to create
     * @param value the value of leaf to create
     */
    public Element(String key, String value){
        this.key = key;
        this.value = value;
        if(value==null){
            this.subentries = new ArrayList<Element>();
        }else{
            this.subentries = null;
        }
    }
    
    /**
     * Add a element (node or leaf) to a node
     * @param element the element to add
     * @return this
     */
    public Element add(Element element){
        if(!isLeaf()){
            subentries.add(element);
            element.setParent(this);
            element.indent = element.buildIndent();
        }
        return this;
    } 
    
    /**
     * Get the key of the element
     * @return the key of the element
     */
    public String getKey(){
        return this.key;
    }
    
    /**
     * Get the value of the element
     * @return the value if it's a leaf
     * @throws MyMlException if it's not a leaf
     */
    public String getValue() throws MyMlException{
        if(isLeaf()){
            return this.value;
        }else{
            throw new MyMlException("You cannot access value of the non-value entry '"+this.key+"'");
        }
    }
        
    /**
     * Check if the entry is a leaf
     * @return true if it's a leaf, false if a node
     */
    public boolean isLeaf(){
        return value!=null;
    }
    
    /**
     * Get indent level of the element in its parent structure
     * @return the indent level
     */
    public int getIndent(){
        return this.indent;
    }
    
    /**
     * Get parent element
     * @return the parent element of this
     */
    public Element getParent(){
        return this.parent;
    }
    
    /**
     * Looks into the subentries to find an entry with the given key
     * @param key the key to look for
     * @return the corresponding entry if any, null if nothing matched
     */
    public Element get(String key){
        for(Element e : subentries){
            if(e.getKey().equals(key)){
                return e;
            }
        }
        return null;
    }
    
    /**
     * Get sub-elements of this
     * @return the list of the sub-elements of this
     */
    public ArrayList<Element> getSubEntries(){
        if(!isLeaf()){
            return subentries;
        }else{
            return null;
        }
    }

    /**
     * Build the list of keys contained in the structure 
     * @return the list containing all the relative keys
     */
    public ArrayList<String> getRelativeKeyList(){
        ArrayList<String> relative_key_list = new ArrayList<String>();
        if(!isLeaf()){
            ArrayList<String> temp_key_lits = new ArrayList<String>();
            for(Element e: this.subentries){
                temp_key_lits.addAll(e.getRelativeKeyList());
            }
            for(String relative_key : temp_key_lits){
                relative_key_list.add(key+"."+relative_key);
            }
        }else{
            relative_key_list.add(key);
        }
        return relative_key_list;
    }
    
    private void setParent(Element parent){
        this.parent = parent;
    }
        
    private int buildIndent(){
        if(this.parent.getKey().equals("root")){
            return 0;
        }else{
            return MyMl.INDENT_LENGTH+this.parent.buildIndent();           
        }
    }
        
    @Override
    public String toString(){
        String s = "";
        String indentation="";
        if(indent>0){
            indentation = String.format("%1$"+indent+"s", "");
        }
        if(isLeaf()){
            s = indentation+key+": "+value+"\n";
        }else{
            s+=indentation+key+":\n";
            for(Element entry : subentries){
                s+=entry.toString();
            }
        }
        return s;
    }
}

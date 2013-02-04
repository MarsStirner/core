package ru.korus.tmis.ws.transfusion.procedure;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        30.01.2013, 15:40:45 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */
public class LaboratoryMeasure {
    /**
     * идентификатор типа лабораторного измерения
     */
    private Integer id;

    /**
     * лабораторные измерения до процедуры
     */
    private String beforeOperation;

    /**
     * лабораторные измерения во время процедуры
     */
    private String duringOperation;

    /**
     * лабораторные измерения в продукте афереза
     */
    private String inProduct;

    /**
     * лабораторные измерения после процедуры
     */
    private String afterOperation;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the beforeOperation
     */
    public String getBeforeOperation() {
        return beforeOperation;
    }

    /**
     * @param beforeOperation the beforeOperation to set
     */
    public void setBeforeOperation(String beforeOperation) {
        this.beforeOperation = beforeOperation;
    }

    /**
     * @return the duringOperation
     */
    public String getDuringOperation() {
        return duringOperation;
    }

    /**
     * @param duringOperation the duringOperation to set
     */
    public void setDuringOperation(String duringOperation) {
        this.duringOperation = duringOperation;
    }

    /**
     * @return the inProduct
     */
    public String getInProduct() {
        return inProduct;
    }

    /**
     * @param inProduct the inProduct to set
     */
    public void setInProduct(String inProduct) {
        this.inProduct = inProduct;
    }

    /**
     * @return the afterOperation
     */
    public String getAfterOperation() {
        return afterOperation;
    }

    /**
     * @param afterOperation the afterOperation to set
     */
    public void setAfterOperation(String afterOperation) {
        this.afterOperation = afterOperation;
    }
    
    

}

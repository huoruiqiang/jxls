package com.jxls.writer;

import org.apache.poi.ss.usermodel.Cell;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Leonid Vysochyn
 *         Date: 2/3/12 12:18 PM
 */
public class CellData {
    public static final String USER_FORMULA_PREFIX = "$[";
    public static final String USER_FORMULA_SUFFIX = "]";

    public enum CellType {
        STRING, NUMBER, BOOLEAN, DATE, FORMULA, BLANK, ERROR
    }

    protected String formula;
    protected int col;
    protected int row;
    protected int sheet;
    protected Object evaluationResult;
    protected Object cellValue;

    protected CellType cellType;
    List<Pos> targetPos = new ArrayList<Pos> ();

    public CellData() {
    }

    public CellData(int sheet, int row, int col, CellType cellType, Object cellValue) {
        this(new Pos(sheet, row, col), cellType, cellValue);
    }
    
    public CellData(Pos pos, CellType cellType, Object cellValue) {
        this.sheet = pos.getSheet();
        this.row = pos.getRow();
        this.col = pos.getCol();
        this.cellType = cellType;
        this.cellValue = cellValue;
        updateFormulaValue();
    }
    

    protected void updateFormulaValue() {
        if( cellType == CellType.FORMULA ){
            formula = cellValue != null ? cellValue.toString() : "";
        }else if( cellType == CellType.STRING && cellValue != null && isUserFormula(cellValue.toString())){
            formula = cellValue.toString().substring(2, cellValue.toString().length() - 1);
        }
    }

    public CellData(int sheet, int row, int col) {
        this(sheet, row, col, CellType.BLANK, null);
    }

    public CellData(int row, int col) {
        this(0, row, col);
    }
    
    public Pos getPos(){
        return new Pos(sheet, row, col);
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }
    
    public Object getCellValue(){
        return cellValue;
    }

    public int getSheet() {
        return sheet;
    }

    public void setSheet(int sheet) {
        this.sheet = sheet;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
    
    public boolean isFormulaCell(){
        return formula != null;
    }

    public static boolean isUserFormula(String str) {
        return str.startsWith(CellData.USER_FORMULA_PREFIX) && str.endsWith(CellData.USER_FORMULA_SUFFIX);
    }
    
    public boolean addTargetPos(Pos pos){
        return targetPos.add(pos);
    }
    
    public List<Pos> getTargetPos(){
        return targetPos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        CellData cellData = (CellData) o;

        if (col != cellData.col) return false;
        if (row != cellData.row) return false;
        if (sheet != cellData.sheet) return false;
        if (cellType != cellData.cellType) return false;
        if (cellValue != null ? !cellValue.equals(cellData.cellValue) : cellData.cellValue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = col;
        result = 31 * result + row;
        result = 31 * result + sheet;
        result = 31 * result + (cellValue != null ? cellValue.hashCode() : 0);
        result = 31 * result + (cellType != null ? cellType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CellData{" +
                "sheet=" + sheet +
                ", row=" + row +
                ", col=" + col +
                ", cellType=" + cellType +
                ", cellValue=" + cellValue +
                '}';
    }
}
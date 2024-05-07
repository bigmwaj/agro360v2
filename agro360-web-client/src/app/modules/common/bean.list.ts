import { SelectionModel } from '@angular/cdk/collections';
import { Component, ViewChild } from '@angular/core';
import { MatTable, MatTableDataSource } from '@angular/material/table';
import { AbstractBean } from 'src/app/backed/bean.common';

@Component({
    standalone: true,
    template: ''
})
export abstract class BeanList<B extends AbstractBean> extends MatTableDataSource<B>{

    constructor(){
        super();
    }

    @ViewChild(MatTable)
    public table: MatTable<B>;

    selection = new SelectionModel<B>(true, []);

    abstract getKeyLabel(bean: B): string | number;

    getData(): B[] {
        return this.data;
    }

    /**
     * @deprecated The method should not be used
     */
    addItem(bean: B) {
        this.data.push(bean);
        if (this.table) {
            this.table.renderRows();
        }
    }

    appendItem(bean: B) {
        this.data.push(bean);
        
        if (this.table) {
            this.table.renderRows();
        }
    }
    
    prependItem(bean: B) {        
        this.data.unshift(bean);
        
        if (this.table) {
            this.table.renderRows();
        }
    }

    removeItem(bean: B) {
        const tmp = this.data.filter(b => b != bean);
        this.data.length = 0

        this.data.push(... tmp)

        if (this.table) {
            this.table.renderRows();
        }
    }
    
    replaceItemWith(bean: B, _with: B) {
        const index = this.data.findIndex(b => b == bean );
        const tmp:Array<B> = [];
        if( index > 0 ){
            tmp.push(...this.data.slice(0, index))
        }

        tmp.push(_with);

        if( index < this.data.length - 1 ){
            tmp.push(...this.data.slice(index + 1))
        }
        
        this.data.length = 0;
        this.data.push(... tmp)
        if (this.table) {
            this.table.renderRows();
        }
    }

    setData(data: B[]) {
        this.data = data
        if (this.table) {
            this.table.renderRows();
        }
    }

    /** Whether the number of selected elements matches the total number of rows. */
    isAllSelected() {
        const numSelected = this.selection.selected.length;
        const numRows = this.data != null ? this.data.length : 0;
        return numSelected === numRows;
    }

    /** Selects all rows if they are not all selected; otherwise clear selection. */
    toggleAllRows() {
        if (this.isAllSelected()) {
            this.selection.clear();
            return;
        }
        this.selection.select(...this.data);
    }

    /** The label for the checkbox on the passed row */
    checkboxLabel(row?: B): string {
        if (!row) {
            return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
        }
        return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${this.getKeyLabel(row)}`;
    }

}

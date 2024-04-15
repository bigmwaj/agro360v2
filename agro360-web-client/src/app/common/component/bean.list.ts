import { SelectionModel } from '@angular/cdk/collections';
import { MatTable, MatTableDataSource } from '@angular/material/table';
import { AbstractBean } from 'src/app/backed/bean.common';

export abstract class BeanList<B extends AbstractBean> extends MatTableDataSource<B>{

    constructor(){
        super();
    }

    selection = new SelectionModel<B>(true, []);

    abstract getViewChild(): MatTable<B>;

    abstract getKeyLabel(bean: B): string | number;

    getData(): B[] {
        return this.data;
    }

    /**
     * @deprecated The method should not be used
     */
    addItem(bean: B) {
        this.data.push(bean);
        if (this.getViewChild()) {
            this.getViewChild().renderRows();
        }
    }

    appendItem(bean: B) {
        this.data.push(bean);
        
        if (this.getViewChild()) {
            this.getViewChild().renderRows();
        }
    }
    
    prependItem(bean: B) {        
        this.data.unshift(bean);
        
        if (this.getViewChild()) {
            this.getViewChild().renderRows();
        }
    }

    removeItem(bean: B) {
        this.data = this.data.filter(b => b != bean);
        if (this.getViewChild()) {
            this.getViewChild().renderRows();
        }
    }

    setData(data: B[]) {
        this.data = data
        if (this.getViewChild()) {
            this.getViewChild().renderRows();
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

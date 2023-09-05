import { DataSource, SelectionModel } from '@angular/cdk/collections';
import { MatTable, MatTableDataSource } from '@angular/material/table';
import { Observable, ReplaySubject } from 'rxjs';
import { AbstractBean } from 'src/app/backed/bean.common';

export abstract class BeanList<B extends AbstractBean> extends MatTableDataSource<B>{

    private _dataStream = new ReplaySubject<B[]>();

    constructor(){
        super();
    }

    //beans: Array<B> = []

    selection = new SelectionModel<B>(true, []);

    abstract getViewChild(): MatTable<B>;

    abstract getKeyLabel(bean: B): string | number;

    getData(): B[] {
        return this.data;
    }

    addItem(bean: B) {
        //this.beans.push(bean);
        this.data.push(bean);
        //this._dataStream.next(this.beans);
        this._dataStream.next(this.data);
        if (this.getViewChild()) {
            this.getViewChild().renderRows();
        }
    }

    removeItem(bean: B) {
        //this.beans = this.beans.filter(b => b != bean);
        this.data = this.data.filter(b => b != bean);
        //this._dataStream.next(this.beans);
        this._dataStream.next(this.data);
        if (this.getViewChild()) {
            this.getViewChild().renderRows();
        }
    }

    setData(data: B[]) {
        this.data = data
        //this.beans = data;
        this._dataStream.next(data);
        if (this.getViewChild()) {
            this.getViewChild().renderRows();
        }
    }

    //connect(): Observable<B[]> {
   //     return this._dataStream;
   // }

   // disconnect() { }

    /** Whether the number of selected elements matches the total number of rows. */
    isAllSelected() {
        const numSelected = this.selection.selected.length;
       // const numRows = this.beans != null ? this.beans.length : 0;
        const numRows = this.data != null ? this.data.length : 0;
        return numSelected === numRows;
    }

    /** Selects all rows if they are not all selected; otherwise clear selection. */
    toggleAllRows() {
        if (this.isAllSelected()) {
            this.selection.clear();
            return;
        }

        //this.selection.select(...this.beans);
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

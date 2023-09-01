import { DataSource, SelectionModel } from '@angular/cdk/collections';
import { MatTable } from '@angular/material/table';
import { Observable, ReplaySubject } from 'rxjs';
import { AbstractBean } from 'src/app/backed/bean.common';

export abstract class BeanList<B extends AbstractBean> extends DataSource<B>{

    private _dataStream = new ReplaySubject<B[]>();

    beans: Array<B> = []

    selection = new SelectionModel<B>(true, []);

    abstract getViewChild(): MatTable<B>;

    abstract getKeyLabel(bean: B): string | number;

    addItem(bean: B) {
        this.beans.push(bean);
        this._dataStream.next(this.beans);
        if (this.getViewChild()) {
            this.getViewChild().renderRows();
        }
    }

    removeItem(bean: B) {
        this.beans = this.beans.filter(b => b != bean);
        this._dataStream.next(this.beans);
        if (this.getViewChild()) {
            this.getViewChild().renderRows();
        }
    }

    setData(data: B[]) {
        this.beans = data;
        this._dataStream.next(data);
        if (this.getViewChild()) {
            this.getViewChild().renderRows();
        }
    }

    connect(): Observable<B[]> {
        return this._dataStream;
    }

    disconnect() { }

    /** Whether the number of selected elements matches the total number of rows. */
    isAllSelected() {
        const numSelected = this.selection.selected.length;
        const numRows = this.beans != null ? this.beans.length : 0;
        return numSelected === numRows;
    }

    /** Selects all rows if they are not all selected; otherwise clear selection. */
    toggleAllRows() {
        if (this.isAllSelected()) {
            this.selection.clear();
            return;
        }

        this.selection.select(...this.beans);
    }

    /** The label for the checkbox on the passed row */
    checkboxLabel(row?: B): string {
        if (!row) {
            return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
        }
        return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${this.getKeyLabel(row)}`;
    }

}

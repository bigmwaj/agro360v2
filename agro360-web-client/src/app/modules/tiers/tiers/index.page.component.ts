import { SelectionModel } from '@angular/cdk/collections';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs';
import { TiersBean, TiersSearchBean } from 'src/app/backed/bean.tiers';

@Component({
    selector: 'tiers-tiers-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {

    searchForm: TiersSearchBean;

    dataSource: MatTableDataSource<TiersBean>;

    @ViewChild(MatPaginator) paginator: MatPaginator;

    displayedColumns: string[] = [
        'select',
        'tiersCode',
        'tiersType',
        'status',
        'tiersName',
        'phone',
        'email',
        'country',
        'city',
        'address',
        'actions'
    ];

    selection = new SelectionModel<TiersBean>(true, []);

    constructor(private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient) { }

    ngOnInit(): void {

        this.http
            .get("http://localhost:8080/tiers/tiers-form/search-tiers-form")
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.searchForm = <TiersSearchBean>data;
            });

        this.http
            .get("http://localhost:8080/tiers/tiers")
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.dataSource = new MatTableDataSource<TiersBean>(data.records);
                this.dataSource.paginator = this.paginator;
            });
    }

    /** Whether the number of selected elements matches the total number of rows. */
    isAllSelected() {
        const numSelected = this.selection.selected.length;
        const numRows = this.dataSource != null ? this.dataSource.data.length : 0;
        return numSelected === numRows;
    }

    /** Selects all rows if they are not all selected; otherwise clear selection. */
    toggleAllRows() {
        if (this.isAllSelected()) {
            this.selection.clear();
            return;
        }

        this.selection.select(...this.dataSource.data);
    }

    /** The label for the checkbox on the passed row */
    checkboxLabel(row?: TiersBean): string {
        if (!row) {
            return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
        }
        return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.tiersCode.value}`;
    }

    addAction() {
        console.log('On crée');
        //this.router.navigate(['create'], { relativeTo: this.route })
    }

    editAction(bean: TiersBean) {
        this.router.navigate(['edit', bean.tiersCode.value], { relativeTo: this.route })
    }

    copyAction(bean: TiersBean) {
        this.router.navigate(['edit', bean.tiersCode.value], { relativeTo: this.route })
    }

    changeStatusAction(bean: TiersBean) {
        // Open Dialog
    }

    deleteAction(bean: TiersBean) {
        // Open Dialog
    }

    editCategoryAction() {
        // Open Dialog
    }
}

import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { map } from 'rxjs';
import { FactureBean, FactureTaxeBean, LigneTaxeBean } from 'src/app/backed/bean.av';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'achat-vente-facture-taxe-list-dialog',
    templateUrl: './taxe.list.dialog.component.html',
})
export class TaxeListDialogComponent implements OnInit {

    bean: FactureBean;

    factureTaxes: FactureTaxeBean[] = []

    displayedColumns: string[] = [
        'taxeCode',
        'taux',
        'description',
        'taxe'
    ];

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: FactureBean,
        private http: HttpClient,
        public dialog: MatDialog,
        public dialogRef: MatDialogRef<TaxeListDialogComponent>) { }

    ngOnInit(): void {
        this.bean = this.data
        let queryParams = new HttpParams();
        queryParams = queryParams.append("factureCode", this.bean.factureCode.value);
        this.http
            .get(`achat-vente/facture/taxe`, { params: queryParams })
            .pipe(map((data: any) => <LigneTaxeBean[]>data))
            .subscribe(data => {
                this.factureTaxes = data;
            });
    }
}

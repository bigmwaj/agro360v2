import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { map } from 'rxjs';
import { CommandeBean, ReglementCommandeBean } from 'src/app/backed/bean.av';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'achat-vente-display-reglement-dialog',
    templateUrl: './display.reglement.dialog.component.html',
})
export class DisplayReglementDialogComponent implements OnInit {

    bean: CommandeBean;

    reglements: ReglementCommandeBean[] = []

    displayedColumns: string[] = [
        
        'date',
        'compte',
        'montant',
    ];

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: CommandeBean,
        private http: HttpClient,
        public dialogRef: MatDialogRef<DisplayReglementDialogComponent>) { }

    ngOnInit(): void {
        this.bean = this.data
        let queryParams = new HttpParams();
        queryParams = queryParams.append("commandeCode", this.bean.commandeCode.value);
        this.http
            .get(`achat-vente/commande/reglement`, { params: queryParams })
            .pipe(map((data: any) => <ReglementCommandeBean[]>data))
            .subscribe(data => {
                this.reglements = data;
            });
    }
}

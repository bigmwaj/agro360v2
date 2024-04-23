import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { map } from 'rxjs';
import { CommandeBean, LigneBean, LigneTaxeBean } from 'src/app/backed/bean.av';
import { SharedModule } from 'src/app/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'achat-vente-display-ligne-taxe-dialog',
    templateUrl: './display.ligne.taxe.dialog.component.html',
})
export class DisplayLigneTaxeDialogComponent implements OnInit {

    bean: LigneBean;

    ligneTaxes: LigneTaxeBean[] = []

    displayedColumns: string[] = [
        'taxeCode',
        'taux',
        'description',
        'taxe'
    ];

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: {ligne:LigneBean, commande: CommandeBean},
        private http: HttpClient,
        public dialog: MatDialog,
        public dialogRef: MatDialogRef<DisplayLigneTaxeDialogComponent>) { }

    ngOnInit(): void {
        this.bean = this.data.ligne
        let queryParams = new HttpParams();
        if( this.bean.ligneId.value){
            queryParams = queryParams.append("ligneId", this.bean.ligneId.value);
        }

        if( this.data.ligne.article.articleCode.value ){
            queryParams = queryParams.append("articleCode", this.data.ligne.article.articleCode.value);
        }else{
            alert('Veuillez selectionner un article!');
            this.dialogRef.close()
            return
        }
        queryParams = queryParams.append("commandeCode", this.data.commande.commandeCode.value);
        this.http
            .get(`achat-vente/commande/ligne/taxe`, { params: queryParams })
            .pipe(map((data: any) => <LigneTaxeBean[]>data))
            .subscribe(data => {
                this.ligneTaxes = data;
            });
    }
}

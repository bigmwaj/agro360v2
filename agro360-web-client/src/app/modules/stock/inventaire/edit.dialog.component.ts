import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { InventaireBean } from 'src/app/backed/bean.stock';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { map } from 'rxjs';
import { Message } from 'src/app/backed/message';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';

@Component({
    standalone: true,
    imports: [
        SharedModule 
    ],
    selector: 'stock-inventaire-edit-dialog',
    templateUrl: './edit.dialog.component.html'
})
export class EditDialogComponent implements OnInit {

    bean: InventaireBean
    constructor(        
        @Inject(MAT_DIALOG_DATA) public data: {bean:InventaireBean, operation:ClientOperationEnumVd},
        private http: HttpClient,
        private ui: UIService,
        public dialog: MatDialog,  
        public dialogRef: MatDialogRef<EditDialogComponent>) {           
    }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('magasinCode', this.data.bean.magasin.magasinCode.value)
        queryParams = queryParams.append('articleCode', this.data.bean.article.articleCode.value)
        queryParams = queryParams.append('variantCode', this.data.bean.variantCode.value)
        queryParams = queryParams.append('operation', this.data.operation)

        this.http
            .get(`stock/inventaire/edit-form`, { params: queryParams })    
            .subscribe(data => {
                this.bean = <InventaireBean>data;
                this.data.bean.uniteAchat.uniteCode.valueOptions = this.bean.uniteAchat.uniteCode.valueOptions;
                this.data.bean.uniteVente.uniteCode.valueOptions = this.bean.uniteVente.uniteCode.valueOptions;
            });
    }

    saveAction():void{    
        this.http.post(`stock/inventaire/ajuster`, this.bean)   
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
                this.dialogRef.close(data.record);
            });
    }
}

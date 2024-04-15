import { HttpClient } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { InventaireBean } from 'src/app/backed/bean.stock';
import { SharedModule } from 'src/app/common/shared.module';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { map } from 'rxjs';
import { Message } from 'src/app/backed/message';
import { UIService } from 'src/app/common/service/ui.service';

@Component({
    standalone: true,
    imports: [
        SharedModule 
    ],
    selector: 'stock-inventaire-ajust-qte-dialog',
    templateUrl: './ajust.qte.dialog.component.html'
})
export class AjustQteDialogComponent implements OnInit {

    bean: InventaireBean
    constructor(        
        @Inject(MAT_DIALOG_DATA) public data: InventaireBean,
        private http: HttpClient,
        private ui: UIService,
        public dialog: MatDialog) {           
    }

    ngOnInit(): void {
        this.bean = this.data
    }

    ajusterAction():void{    
        this.http.post(`stock/inventaire/ajuster-quantite`, this.bean)   
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
                this.dialog.closeAll();
            });
    }
}

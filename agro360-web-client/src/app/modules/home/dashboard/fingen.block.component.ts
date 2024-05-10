import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { map } from 'rxjs';
import { EtatDetteBean } from 'src/app/backed/bean.av';
import { EtatCompteBean } from 'src/app/backed/bean.finance';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule,     
    ],
    selector: 'home-dashboard-fingen-block',
    templateUrl: './fingen.block.component.html'
})
export class FinGenBlockComponent implements OnInit {

    etatDettes: EtatDetteBean[]
    
    constructor(
        private http: HttpClient) {
    }

    ngOnInit(): void {
        this.http        
            .get(`achat-vente/facture/generate-etat-dette`)  
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.etatDettes = data.records;
            });
    }
}

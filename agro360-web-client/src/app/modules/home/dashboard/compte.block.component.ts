import { Component, OnInit } from '@angular/core';
import { SharedModule } from 'src/app/common/shared.module';
import { map } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { EtatCompteBean } from 'src/app/backed/bean.finance';

@Component({
    standalone: true,
    imports: [
        SharedModule,     
    ],
    selector: 'home-dashboard-compte-block',
    templateUrl: './compte.block.component.html'
})
export class CompteBlockComponent implements OnInit {
    
    etatComptes: EtatCompteBean[]

    totaux:number = 0

    constructor(private http: HttpClient) {
    }

    ngOnInit(): void {
        this.http        
            .get(`finance/compte/generate-etat-compte`)  
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.etatComptes = data.records;
                this.totaux = this.etatComptes.map(e => e.solde.value).reduce((a,b)=>a+b)
            });
    }
}

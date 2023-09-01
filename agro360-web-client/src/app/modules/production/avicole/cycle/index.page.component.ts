import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs';
import { CycleBean } from 'src/app/backed/bean.production.avicole';
import { SharedModule } from 'src/app/common/shared.module';

interface CycleModel extends CycleBean{
    selected:boolean
}

@Component({
    standalone: true,
    imports: [
        CommonModule,
        MatButtonModule,
        MatIconModule,
        MatDialogModule,
        MatCheckboxModule,
        MatTableModule,
        SharedModule
    ],
    selector: 'production-avicole-cycle-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {

    
    beans : Array<CycleModel>;

    constructor(private router: Router,
        private route:ActivatedRoute, 
        private http:HttpClient) { }

    ngOnInit(): void {
        this.http
        .get("http://localhost:8080/production/avicole/cycle")
        .pipe(map((data:any) => data))
        .subscribe(data => this.beans = <Array<CycleModel>>data.records);
    }

    addAction(){
        console.log('On crée');
        //this.router.navigate(['create'], { relativeTo: this.route })
    }

    editAction(bean:CycleModel){
        this.router.navigate(['edit', bean.cycleCode.value], { relativeTo: this.route })
    }

    copyAction(bean:CycleModel){
        this.router.navigate(['edit', bean.cycleCode.value], { relativeTo: this.route })
    }

    changeStatusAction(bean:CycleModel){
        // Open Dialog
    }

    deleteAction(bean:CycleModel){
        // Open Dialog
    }

    editCategoryAction(){
        // Open Dialog
    }
}

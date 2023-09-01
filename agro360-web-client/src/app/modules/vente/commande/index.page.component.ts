import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs';
import { CommandeBean, CommandeSearchBean } from 'src/app/backed/bean.vente';
import { BeanList } from 'src/app/common/bean.list';
import { CommonUtlis } from 'src/app/common/utils/common.utils';
import { SharedModule } from 'src/app/common/shared.module';
import { MatTableModule } from '@angular/material/table';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';

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
    selector: 'achat-commande-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent extends BeanList<CommandeBean> implements OnInit {

    searchForm: CommandeSearchBean;

    displayedColumns: string[] = [
        'select',
        'commandeCode',
        'description',
        'dateCommande', 
        'livree', 
        'status',
        'magasin',
        'client',
        'adresse',
        'transportRequis',
        'ville',
        'actions'
    ];
    
    @ViewChild(MatTable)
    public table: MatTable<CommandeBean>;

    constructor(private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient) {
        super()
    }

    getKeyLabel(bean: CommandeBean): string {
        return bean.commandeCode.value;
    }

    override getViewChild(): MatTable<CommandeBean> {
        return this.table;
    }

    ngOnInit(): void {
        this.resetSearchFormAction()
    }

    resetSearchFormAction() {
        this.http
            .get(`${CommonUtlis.BASE_URL}/vente/commande/search-form`)
            .subscribe(data => {
                this.searchForm = <CommandeSearchBean>data;
                this.searchAction();
            });
    }

    searchAction() {
        this.http
            .get(`${CommonUtlis.BASE_URL}/vente/commande`, { params: CommonUtlis.encodeQuery(this.searchForm) })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }

    addAction() {
        this.router.navigate(['create'], { relativeTo: this.route })
    }

    editAction(bean: CommandeBean) {
        this.router.navigate(['edit', bean.commandeCode.value], { relativeTo: this.route })
    }

    copyAction(bean: CommandeBean) {
        this.router.navigate(['create'], { relativeTo: this.route, queryParams: { 'copyFrom': bean.commandeCode.value } })
    }

    deleteAction(bean: CommandeBean) {
        // Open Dialog
    }
    
    changeStatusAction(bean: CommandeBean) {
        // Open Dialog
    }
}

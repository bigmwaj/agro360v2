
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { map } from 'rxjs';
import { SharedModule } from 'src/app/common/shared.module';
import { UIService } from 'src/app/common/service/ui.service';
import { ActivatedRoute } from '@angular/router';
import { MatTabsModule } from '@angular/material/tabs';
import { ProductionAvicoleService } from '../production.avicole.service';
import { JourneeBean, JourneeSearchBean } from 'src/app/backed/bean.production.avicole';
import { IndexProducitonListComponent } from './index.production.list.component';
import { BeanTools } from 'src/app/common/bean.tools';
import { Message } from 'src/app/backed/message';

@Component({
    standalone: true,
    imports: [
        SharedModule,
        MatTabsModule,
        IndexProducitonListComponent
    ],
    selector: 'production-avicole-journee-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {

    searchForm: JourneeSearchBean;

    cycleCode: string | null

    bean: JourneeBean

    constructor(
        private http: HttpClient,
        private route: ActivatedRoute,
        private ui: UIService,
        private service: ProductionAvicoleService) {
    }

    ngOnInit(): void {
        this.route.paramMap.subscribe(params => {
            this.cycleCode = params.get('cycleCode');
        });
        this.resetSearchFormAction()
    }

    resetSearchFormAction() {
        this.http
            .get(this.service.getBackendUrl(`production/avicole/journee/search-form`))
            .subscribe(data => {
                this.searchForm = <JourneeSearchBean>data;                
                if( this.cycleCode ){
                    this.searchForm.cycleCode.value = this.cycleCode
                }
                this.searchForm.journee.value = new Date()
                this.searchAction();
            });
    }

    searchAction() {
        
        let objJsonStr = JSON.stringify(this.searchForm);
        let objJsonB64 = btoa(objJsonStr);

        let queryParams = new HttpParams();
        queryParams = queryParams.append('q', objJsonB64);
        this.http
            .get(this.service.getBackendUrl(`production/avicole/journee`), { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.bean = data.records[0]
            });
    }

    saveAction() {
        this.http.post(this.service.getBackendUrl(`production/avicole/journee`), BeanTools.reviewBeanAction(this.bean))
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            this.ui.displayFlashMessage(<Array<Message>>data.messages);
        });
    }
}

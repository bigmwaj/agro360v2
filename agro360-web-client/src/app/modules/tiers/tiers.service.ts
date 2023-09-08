import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CommonService } from 'src/app/common/service/common.service';

@Injectable({
    providedIn:'root'
})
export class TiersService extends CommonService {

    constructor(public override _snackBar: MatSnackBar){
        super(_snackBar);
    }
    
}
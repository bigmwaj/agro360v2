import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { AppTools } from "../../app-tools";
import { OptionModel } from "../../models/option-model";
import { UtilsService } from "../../services/utils.service";
import { EditableListAttribute } from "./editable-list.attribute";
import { EditableListElement } from "./editable-list.element";

export abstract class EditableListComponent<T extends EditableListElement>{
    
    p_editableList = this;

    constructor(public utils: UtilsService){}

    abstract getAttributesConfig():EditableListAttribute[]
    
    getSelectAttrOptions(attrName:string):OptionModel[]{    
        return [];
    }

    abstract getDataSource():T[];

    abstract newInstance(options:any):T

    initAdd():T{
        const e = this.newInstance({});
        e.isnew = true;
        this.getDataSource().push(e)
        return e;
    } 

    protected getFromBackend(e:T):Observable<T>|null{
        throw new Error("Method not implemented.");
    }
    
    cancel(e:T){
        if( e.isnew ){// Remove
            AppTools.arrayRemoveWithComparator(this.getDataSource(), t => e.equals(t))
        }else if(e.updated){// Replate with the original
            const d = this.getFromBackend(e);
            if( d != null ){
                d.subscribe((data:T) => AppTools.arrayReplace(this.getDataSource(), e, this.newInstance(data)))
            }
        }else{//Toggle delete only
            e.deleted = e.updated = false;
        }
    }

    save(e:T){
        if( e.isnew ){
            const d = this.processAdd(e);
            if( d != null ){
                d
                .pipe(map((data:any) => this.newInstance(data)))
                .subscribe(data => {
                    this.utils.notify("Ajout enregistrement", "Succès!");
                    AppTools.arrayReplace(this.getDataSource(), e, data)          
                })
            }
        }else if(e.deleted){
            if(!e.deleteAllowed()){
                this.utils.notify("Suppression", "Operation non autorisée!");
                return
            }
            const d = this.processDelete(e);
            if( d != null ){
                d
                .pipe(map((data:any) => this.newInstance(data)))
                .subscribe(data => {
                    this.utils.notify("Suppression enregistrement", "Succès!");
                    AppTools.arrayRemoveWithComparator(this.getDataSource(), t => e.equals(t))
                })
            }
        }else{
            if( !e.updateAllowed()){
                this.utils.notify("Modification", "Operation non autorisée!");
                return
            }
            const d = this.processUpdate(e);
            if( d != null ){
                d
                .pipe(map((data:any) => this.newInstance(data)))
                .subscribe(data => {
                   this.utils.notify("Modification enregistrement", "Succès!");
                   AppTools.arrayReplaceWithComparator(this.getDataSource(), data, t => data.equals(t))          
                })
            }
        }    
    }

    protected abstract processAdd(e:T):Observable<T>;

    protected processUpdate(e:T):Observable<T>|null{
        throw new Error("Method not implemented.");
    }
    
    protected processDelete(e:T):Observable<T>|null{
        throw new Error("Method not implemented.");
    }
}
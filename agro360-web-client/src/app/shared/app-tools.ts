export class AppTools {
    /**
     * 
     * @param arrays 
     * @param oldValue doit être présente dans la liste
     * @param newValue nouvel élément à ajouter
     */
    static arrayReplace<T>(arrays:T[], oldValue:T, newValue:T){
        const i = arrays.findIndex( e => e == oldValue);
        if( i != -1 ){
            arrays[i] = newValue
        }
    }

    static arrayReplaceWithComparator<T>(arrays:T[], newValue:T, comp:(e:T) => boolean){
        const i = arrays.findIndex( e => comp(e));
        if( i != -1 ){
            arrays[i] = newValue
        }
    }

    static arrayRemoveWithComparator<T>(arrays:T[], comp:(t:T) => boolean){
        const pos = arrays.findIndex( e => comp(e));
        if( pos != -1 ){
            for (let i = pos; i < arrays.length -1 ; i++) {
                arrays[i] = arrays[i+1];                
            }
            arrays.pop()
        }
    }
}

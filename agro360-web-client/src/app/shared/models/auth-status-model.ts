/**
 * @summary Statut autorisé.
 * @description Lorsqu'un microservice expose une DTO orchestrée(ayant des statuts), 
 * il renvoit à la même occasion une liste des statuts dont on peut assigner à la DTO. 
 */
export interface AuthStatusModel{
    /**
     * Statut assignable à la DTO
     */
    status: string;

    /**
     * Libellé(déjà traduit) du statut
     */
    label: string;
}
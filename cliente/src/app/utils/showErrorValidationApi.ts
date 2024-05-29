export default class ApiErrorMessages{
    /**
     * adiciona no objeto de erros do componente a resposta de erro da api pra cada campo
     * @param obj 
     * @param errors 
     */
    static buildErrorByInputs(obj: any, errors: any){
        const keys = Object.keys(obj);        
        keys.forEach(k => {            
            if(errors.error.hasOwnProperty(k)){
                obj[k] = errors.error[k];
            }
            else{
                obj[k] = null;
            }
        });        
    }
}
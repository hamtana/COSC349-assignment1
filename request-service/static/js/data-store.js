export const dataStore = Vuex.createStore({

    //Where we declare the state of the item we want to keep track of
    state(){
        // Tenant that is signed in
        tenant: null;

        // The request that is being made/edited
        request: null;

    }

    mutations: {
        // tenant signs in
        signIn(state, tenant){
            state.tenant = tenant;
        }
    },

        // add session storage persistence
        plugins: [window.createPersistedState({storage: window.sessionStorage})]


});
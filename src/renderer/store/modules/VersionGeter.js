const state = {
  version: 0,
};

const mutations = {
  SET_VERSION(state, payload) {
    state.version = stat;
  },
  INCREMENT_MAIN_COUNTER(state) {
    state.main += 1;
  },
};

const actions = {
  getVersion({ commit }) {
    ax
    payload.version = '1';
    commit('SET_VERSION', payload);
  },
};

export default {
  state,
  mutations,
  actions,
};

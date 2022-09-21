import { createApp } from "vue";
import { createPinia } from "pinia";
import App from "./App.vue";
import router from "./router";
import vuetify from "./plugins/vuetify";
import vue3GoogleLogin from "vue3-google-login";
import { loadFonts } from "./plugins/webfontloader";
loadFonts();

const pinia = createPinia();
const app = createApp(App);

app
  .use(pinia)
  .use(router)
  .use(vuetify)
  .use(vue3GoogleLogin, {
    clientId:
      "603496138054-ap705pl2e3qpvgp856mp0aubcb8mj33v.apps.googleusercontent.com",
  })
  .mount("#app");

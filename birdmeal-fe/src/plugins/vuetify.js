// Styles
import '@mdi/font/css/materialdesignicons.css';
import 'vuetify/styles';

// Vuetify
import { createVuetify } from 'vuetify';

export default createVuetify(
  // https://vuetifyjs.com/en/introduction/why-vuetify/#feature-guides
  {
    theme: {
      themes: {
        light: {
          dark: false,
          colors: {
            // birdmeal custom color
            back_beige: '#F2EEE2',
            secondary_orange: '#CF603F',
            primary_orange: '#E26749',
            dark_orange: '#F4BB7D',
            green: '#658179',
          },
        },
      },
    },
  }
);

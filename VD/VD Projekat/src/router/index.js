import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'  //vukasin007
import About from '../views/About.vue'  //vukasin007
import Ptice from '../views/Ptice.vue'
import Macke from '../views/Macke.vue'
import Psi from '../views/Psi.vue'
import Ptica from '../views/Ptica.vue'
import Macka from '../views/Macka.vue'
import Pas from '../views/Pas.vue'
import Izgubljeni from '../views/Izgubljeni.vue'  //vukasin007
import Oglas from '../views/Oglas.vue'  //vukasin007
import Nalog from '../views/Nalog.vue'  //vukasin007
import Dodaj from '../views/Dodaj.vue'  //vukasin007


const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    component: About,
  },
  {
    path: '/ptice',
    name: 'Ptice',
    component: Ptice,
  },
  {
    path: '/macke',
    name: 'Macke',
    component: Macke,
  },
  {
    path: '/psi',
    name: 'Psi',
    component: Psi,
  },
  {
    path: '/ptice/:id',
    name: 'Ptica',
    component: Ptica,
  },
  {
    path: '/macke/:id',
    name: 'Macka',
    component: Macka,
  },
  {
    path: '/psi/:id',
    name: 'Pas',
    component: Pas,
  },
  {
    path: '/izgubljeni',
    name: 'Izgubljeni',
    component: Izgubljeni, 
  },
  {
    path: '/oglas/:id',
    name: 'Oglas',
    component: Oglas, 
  },
  {
    path: '/nalog',
    name: 'Nalog',
    component: Nalog, 
  },
  {
    path: '/dodaj',
    name: 'Dodaj',
    component: Dodaj, 
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router



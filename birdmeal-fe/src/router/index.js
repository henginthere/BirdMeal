import { createRouter, createWebHistory } from 'vue-router';
import { authState } from '@/stores/auth';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/home',
    },
    {
      path: '/guide',
      name: 'guide',
      component: () => import('@/views/GuideView.vue'),
    },
    {
      path: '/signin',
      name: 'signin',
      component: () => import('@/views/SigninView.vue'),
    },
    {
      path: '/main',
      name: 'main',
      component: () => import('@/views/MainView.vue'),
      children: [
        {
          path: '/home',
          name: 'home',
          component: () => import('@/views/HomeView.vue'),
        },
        {
          path: '/test',
          name: 'test',
          component: () => import('@/views/TestView.vue'),
        },

        {
          path: '/mypage',
          name: 'mypage',
          component: () => import('@/views/MyPageView.vue'),
        },
        {
          path: '/products',
          name: 'products',
          component: () => import('@/views/ProductsView.vue'),
        },
        {
          path: '/products/detail/:productSeq',
          name: 'product-detail',
          component: () => import('@/views/ProductDetailView.vue'),
        },
        {
          path: '/product/regist',
          name: 'product-regist',
          component: () => import('@/views/ProductRegistView.vue'),
        },
        {
          path: '/orders',
          name: 'orders',
          component: () => import('@/views/OrdersView.vue'),
        },
        {
          path: '/wallet',
          name: 'wallet',
          component: () => import('@/views/WalletView.vue'),
        },
      ],
    },
  ],
});

// 네비게이션 가드
router.beforeEach((to, from, next) => {
  const auth = authState();
  if (auth.user) next();
  else {
    if (to.name == 'signin' || to.name == 'guide') {
      next();
    } else {
      next('/signin');
    }
  }
});

export default router;

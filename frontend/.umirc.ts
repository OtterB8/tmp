import { defineConfig, history } from 'umi';

export default defineConfig({
  layout:false,
  nodeModulesTransform: {
    type: 'none',
  },
  routes: [
    {
      path: '/',
      component: '@/layouts',
      routes: [
        { path: '/chatroom', component: 'ChatRoom'},
        { path: '/signin', component: 'SignIn', exact: true},
        { path: '/signup', component: 'SignUp', exact: true},
      ],
      wrappers: ['@/wrappers/AuthWrapper.tsx']
    },
  ],
});

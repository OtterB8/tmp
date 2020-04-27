import { defineConfig } from 'umi';

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
        { path: '/chatroom', component: 'ChatRoom', exact: true},
        { path: '/signin', component: 'SignIn', exact: true},
        { path: '/signup', component: 'SignUp', exact: true},
      ],
      wrappers: ['@/wrappers/auth.tsx'],
    },
  ],
});

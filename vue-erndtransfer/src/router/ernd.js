/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const erndRouter = {
  path: '/ernd',
  component: Layout,
  redirect: '/ernd/mapping/bsns',
  name: 'dataTransfer',
  meta: {
    title: '웹이관프로그램',
    icon: 'folder-solid'
  },
  children: [
    {
      path: 'mapping',
      name: '데이터 정제',
      component: () => import('@/ernd/view/MappingIndex'),
      meta: { title: '데이터 정제', icon: 'folder-solid', noCache: true },
      redirect: '/ernd/bsns',
      children: [
        {
          path: 'bsns',
          component: () => import('@/ernd/view/Bsns'),
          name: '사업',
          meta: { title: '사업', icon: 'briefcase-solid', noCache: true }
        },
        {
          path: 'itepd',
          component: () => import('@/ernd/view/Itepd'),
          name: '비목',
          meta: { title: '비목', icon: 'file-invoice-solid', noCache: true }
        },
        {
          path: 'ancm',
          component: () => import('@/ernd/view/Ancm'),
          name: '공고',
          meta: { title: '공고', icon: 'briefcase-solid', noCache: true }
        },
        {
          path: 'sbjt',
          component: () => import('@/ernd/view/Sbjt'),
          name: '과제',
          meta: { title: '과제', icon: 'briefcase-solid', noCache: true }
        }
      ]
    },
    {
      path: 'trns',
      name: '데이터 이관',
      component: () => import('@/ernd/view/TrnsIndex'),
      meta: { title: '데이터 이관', icon: 'folder-solid', noCache: true },
      children: [
        {
          path: 'all',
          component: () => import('@/ernd/view/TrnsAll'),
          name: '전체 이관',
          meta: { title: '전체 이관', icon: 'database-solid', noCache: true }
        },
        {
          path: 'each',
          component: () => import('@/ernd/view/TrnsEach'),
          name: '개별 이관',
          meta: { title: '개별이관(과제별)', icon: 'share-from-square-solid', noCache: true }
        }
      ]
    },
    {
      path: 'result',
      name: '데이터 이관결과',
      component: () => import('@/ernd/view/ResultIndex'),
      meta: { title: '데이터 이관결과', icon: 'folder-solid', noCache: true },
      children: [
        {
          path: 'dataTrnsfRslt',
          component: () => import('@/ernd/view/DataTrnsfRslt'),
          name: '이관 진행 결과',
          meta: { title: '이관 진행 결과', icon: 'magnifying-glass-solid', noCache: true }
        },
        {
          path: 'inquiry',
          component: () => import('@/ernd/view/ResultInq'),
          name: '이관결과 미리보기',
          meta: { title: '이관결과 미리보기', icon: 'magnifying-glass-solid', noCache: true }
        }
      ]
    }
  ]
}

export default erndRouter

import {createRouter, createWebHistory} from 'vue-router'
import FrontLayout from "@/views/layout/FrontLayout.vue";
import AdminLayout from "@/views/layout/AdminLayout.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: getRoutes()
})

function getRoutes() {
    let defaultRoutes = [
        {
            path: '/',
            name: 'front',
            component: FrontLayout,
            redirect: "/index",
            children: [{
                path: "index",
                name: "index",
                component: () =>
                    import ('../views/front/Index.vue')
            }, {
                path: "editCurrentUser",
                name: "editCurrentUser",
                component: () =>
                    import ('../views/EditCurrentUser.vue')
            }, {
                path: "editPassword",
                name: "front-editPassword",
                component: () =>
                    import ('../views/EditPassword.vue')
            },{
                path: "balanceInfo",
                name: "front-balanceInfo",
                component: () =>
                    import ('../views/BalanceInfo.vue')
            },
                {
                    path: 'announcement',
                    name: 'announcement',
                    component: () => import('../views/front/Announcement.vue')
                },
                {
                    path: 'news',
                    name: 'news',
                    component: () => import('../views/front/News.vue')
                },
                {
                    path: 'forum',
                    name: 'forum',
                    component: () => import('../views/front/Forum.vue')
                },
                {
                    path: 'personalCenter',
                    name: 'personalCenter',
                    component: () => import('../views/front/PersonalCenter.vue')
                },
                {
                    path: 'announcementDetails/:id',
                    name: 'front-announcementDetails',
                    component: () => import('../views/front/AnnouncementDetails.vue')
                },
                {
                    path: 'newsDetails/:id',
                    name: 'front-newsDetails',
                    component: () => import('../views/front/NewsDetails.vue')
                },
                {
                    path: 'forumDetails/:id',
                    name: 'front-forumDetails',
                    component: () => import('../views/front/ForumDetails.vue')
                },
            ]
        },
        {
            path: '/admin',
            name: 'admin',
            component: AdminLayout,
            redirect: "/admin/home",
            children: [{
                path: "home",
                name: "admin-home",
                component: () =>
                    import ('../views/admin/Home.vue')
            },
                {
                    path: 'editCurrentUser',
                    name: 'admin-editCurrentUser',
                    component: () =>
                        import ('../views/EditCurrentUser.vue')
                },
                {
                    path: 'editPassword',
                    name: 'admin-editPassword',
                    component: () =>
                        import ('../views/EditPassword.vue')
                },
                {
                    path: 'admin',
                    name: 'Admin',
                    component: () =>
                        import ('../views/admin/AdminManage.vue')
                },
                {
                    path: 'user',
                    name: 'admin-user',
                    component: () =>
                        import ('../views/admin/UserManage.vue')
                },
                {
                    path: 'complaint',
                    name: 'admin-complaint',
                    component: () =>
                        import ('../views/admin/ComplaintManage.vue')
                }
                ,
                {
                    path: 'repair',
                    name: 'admin-repair',
                    component: () =>
                        import ('../views/admin/RepairManage.vue')
                }
                // ,
                // {
                //     path: 'meterReading',
                //     name: 'admin-meterReading',
                //     component: () =>
                //         import ('../views/admin/MeterReadingManage.vue')
                // }
                ,
                {
                    path: 'propertyFee',
                    name: 'admin-propertyFee',
                    component: () =>
                        import ('../views/admin/PropertyFeeManage.vue')
                }
                ,
                {
                    path: 'parkingFee',
                    name: 'admin-parkingFee',
                    component: () =>
                        import ('../views/admin/ParkingFeeManage.vue')
                }
                ,
                {
                    path: 'parkingSpace',
                    name: 'admin-parkingSpace',
                    component: () =>
                        import ('../views/admin/ParkingSpaceManage.vue')
                }
                ,
                {
                    path: 'parkingLot',
                    name: 'admin-parkingLot',
                    component: () =>
                        import ('../views/admin/ParkingLotManage.vue')
                }
                ,
                {
                    path: 'house',
                    name: 'admin-house',
                    component: () =>
                        import ('../views/admin/HouseManage.vue')
                }
                ,
                {
                    path: 'building',
                    name: 'admin-building',
                    component: () =>
                        import ('../views/admin/BuildingManage.vue')
                }
                ,
                {
                    path: 'forumComment',
                    name: 'admin-forumComment',
                    component: () =>
                        import ('../views/admin/ForumCommentManage.vue')
                }
                ,
                {
                    path: 'forum',
                    name: 'admin-forum',
                    component: () =>
                        import ('../views/admin/ForumManage.vue')
                }
                ,
                {
                    path: 'newsComment',
                    name: 'admin-newsComment',
                    component: () =>
                        import ('../views/admin/NewsCommentManage.vue')
                }
                ,
                {
                    path: 'news',
                    name: 'admin-news',
                    component: () =>
                        import ('../views/admin/NewsManage.vue')
                }
                ,
                {
                    path: 'announcement',
                    name: 'admin-announcement',
                    component: () =>
                        import ('../views/admin/AnnouncementManage.vue')
                },
                {
                    path: 'utilityBillFee',
                    name: 'admin-utilityBillFee',
                    component: () =>
                        import ('../views/admin/UtilityBillFeeManage.vue')
                },
            ]
        },
        {
            path: "/login",
            name: "login",
            component: () =>
                import ('../views/Login.vue')
        }, {
            path: "/register",
            name: "register",
            component: () =>
                import ('../views/Register.vue')
        }, {
            path: "/retrievePassword",
            name: "front-retrievePassword",
            component: () =>
                import ('../views/RetrievePassword.vue')
        }];
    defaultRoutes.push({
        path: '/:pathMatch(.*)*',
        name: 'NotFound',
        meta: {
            name: ''
        },
        component: () => import ('../views/404.vue')
    })
    console.log('getDynamicRoutes', defaultRoutes)
    return defaultRoutes;
}

router.beforeEach((to, from, next) => {
    next();
});
export default router

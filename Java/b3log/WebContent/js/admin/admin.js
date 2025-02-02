/*
 * Copyright (c) 2009, 2010, 2011, 2012, B3log Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *  index for admin
 *
 * @author <a href="mailto:LLY219@gmail.com">Liyuan Li</a>
 * @version 1.0.2.0, Aug 30, 2012
 */

var Admin = function () {
    this.register = {};
    // 工具栏下的工具
    this.tools = ['#page-list', '#file-list', '#link-list', '#preference', 
    '#user-list', '#plugin-list', '#others'];
    // 多用户时，一般用户不能使用的功能
    this.adTools = ['link-list', 'preference', 'file-list', 'page-list',
    'user-list', 'plugin-list', 'others'];
};

$.extend(Admin.prototype, {    
    /*
     * 登出
     */
    logout: function () {
        window.location.href = latkeConfig.servePath + "/logout?goto=" + latkeConfig.servePath;
    },
    
    /*
     * 根据当前页数设置 hash
     * @currentPage {string} 当前页
     */
    setHashByPage: function (currentPage) {
        var hash = window.location.hash,
        hashList = hash.split("/");
        if (/^\d*$/.test(hashList[hashList.length - 1])) {
            hashList[hashList.length - 1] = currentPage;
        } else {
            hashList.push(currentPage);
        }
        window.location.hash = hashList.join("/");
    },
    
    /*
     * 设置某个 tab 被选择
     * @id tab id
     */
    selectTab: function (id) {
        window.location.hash = "#" + id;
    },
    
    /*
     * 根据当前 hash 解析出当前页数及 hash 数组。
     */
    analyseHash: function () {
        var hash = window.location.hash;
        var tag = hash.substr(1, hash.length - 1);
        var tagList = tag.split("/");
        var tags = {};
        tags.page = 1,
        tags.hashList = [];
        for (var i = 0; i < tagList.length; i++) {
            if (i === tagList.length - 1 && (/^\d+$/.test(tagList[i]))) {
                tags.page = tagList[i];
            } else {
                tags.hashList.push(tagList[i]);
            }
        }
        return tags;
    },
    
    /*
     * 根据当前 hash 设置当前 tab
     */
    setCurByHash: function () {
        var tags = admin.analyseHash();
        var tab = tags.hashList[1], 
        subTab = tags.hashList[2];
        
        if (tags.hashList.length === 1) {
            tab = tags.hashList[0];
        }
        
        if (tab === "") {
            return;
        }
        
        // 离开编辑器时进行提示
        try {
            // 除更新、发布、取消发布文章，编辑器中无内容外，离开编辑器需进行提示。
            if (tab !== "article" && admin.article.isConfirm &&
                admin.editorArticle.getContent().replace(/\s/g, '') !== "") {
                if (!confirm(Label.editorLeaveLabel)) {
                    window.location.hash = "#article/article";
                    return;
                }
            }
            // 不离开编辑器，hash 需变为 "#article/article"，此时不需要做任何处理。
            if (tab === "article" && admin.article.isConfirm &&
                admin.editorArticle.getContent().replace(/\s/g, '') !== "") {
                return;
            }
        } catch (e) {
            var $articleContent =  $('#articleContent');
            if ($articleContent.length > 0) {
                if (tab !== "article" && admin.article.isConfirm &&
                    $articleContent.val().replace(/\s/g, '') !== "") {
                    if (!confirm(Label.editorLeaveLabel)) {
                        window.location.hash = "#article/article";
                        return;
                    }
                }
                // 不离开编辑器，hash 需变为 "#article/article"，此时不需要做任何处理。
                if (tab === "article" && admin.article.isConfirm &&
                    $articleContent.val().replace(/\s/g, '') !== "") {
                    return;
                }
            }
        }
        
        // clear article 
        if (tab !== "article" && admin.editorArticle.setContent) {
            admin.article.clear();
        }
        admin.article.isConfirm = true;
        
        $("#tabs").tabs("setCurrent", tab);
        $("#loadMsg").text(Label.loadingLabel);
        
        if ($("#tabsPanel_" + tab).length === 1) {
            if ($("#tabsPanel_" + tab).html().replace(/\s/g, "") === "") {
                // 还未加载 HTML
                $("#tabsPanel_" + tab).load("admin-" + tab + ".do", function () {
                    // 页面加载完后，回调初始函数
                    if (tab === "article" && admin.article.status.id) {
                        // 当文章页面编辑器未初始化时，调用更新文章需先初始化编辑器
                        admin.register[tab].init.call(admin.register[tab].obj, admin.article.getAndSet);
                    } else {
                        admin.register[tab].init.call(admin.register[tab].obj, tags.page);
                    }
                
                    // 页面包含子 tab，需根据 hash 定位到相应的 tab
                    if (subTab) {
                        $("#tab" + tab.substring(0, 1).toUpperCase() + tab.substring(1)).
                        tabs("setCurrent", subTab);
                    }
                
                    // 根据 hash 调用现有的插件函数
                    admin.plugin.setCurByHash(tags);
                });
            } else {
                if (tab === "article" && admin.article.status.id) {
                    admin.article.getAndSet();
                }
            
                // 已加载过 HTML，只需调用刷新函数
                if (admin.register[tab] && admin.register[tab].refresh) {
                    admin.register[tab].refresh.call(admin.register[tab].obj, tags.page);
                }
            
                // 页面包含子 tab，需根据 hash 定位到相应的 tab
                if (subTab) {
                    $("#tab" + tab.substring(0, 1).toUpperCase() + tab.substring(1)).
                    tabs("setCurrent", subTab);
                }
                
                // 根据 hash 调用现有的插件函数
                admin.plugin.setCurByHash(tags);
            }  
        } else {
            $("#tipMsg").text("Error: No tab! " + Label.reportIssueLabel);
            $("#loadMsg").text("");
        }
    },
    
    /*
     * 初始化整个后台
     */
    init: function () {
        //window.onerror = Util.error;
        
        Util.killIE();   
        $("#loadMsg").text(Label.loadingLabel);
        
        // 构建 tabs
        $("#tabs").tabs();
            
        // tipMsg
        setInterval(function () {
            if($("#tipMsg").text() !== "") {
                setTimeout(function () {
                    $("#tipMsg").text("");
                }, 7000);
            }
        }, 6000);
        $("#loadMsg").text("");
    },
    
    /*
     * @description tools and article collapse
     * @param {bom} it 触发事件对象
     */
    collapseNav: function (it) {
        var subNav = $(it).next();
        subNav.slideToggle("normal", function () {
            if (this.style.display !== "none") {
                $(it).find(".ico-arrow-down")[0].className = "ico-arrow-up";
            } else {
                $(it).find(".ico-arrow-up")[0].className = "ico-arrow-down";
            }
        });
    },
    
    /*
    * 后台及当前页面所需插件初始化完后，对权限进行控制及当前页面属于 tools 时，tools 选项需展开。
    */
    inited: function () {
        // Removes functions with the current user role
        if (Label.userRole !== "adminRole") {
            for (var i = 0; i < this.adTools.length; i++) {
                $("#tabs").tabs("remove", this.adTools[i]);
            }
        } else {
            // 当前 tab 属于 Tools 时，设其展开
            for (var j = 0; j < this.tools.length; j++) {
                if ("#" + window.location.hash.split("/")[1] === this.tools[j]) {
                    $("#tabToolsTitle").click();
                    break;
                }
            }
        }
        this.setCurByHash();
    }
});

var admin = new Admin();
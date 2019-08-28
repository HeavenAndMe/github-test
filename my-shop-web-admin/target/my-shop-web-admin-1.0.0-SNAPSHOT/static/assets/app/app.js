var App = function () {

    //iCheck
    var _masterCheckbox = null;
    var _checkbox = null;

    //用于存放 ID 的数组
    var _idArray;

    var defaultDropzoneOpts =  {
        url: "/", // 文件提交地址
        paramName: "dropFile", // 默认为file
        maxFiles: 1,// 一次性上传的文件数量上限
        maxFilesize: 2, // 文件大小，单位：MB
        acceptedFiles: ".jpg,.gif,.png,.jpeg", // 上传的类型
        addRemoveLinks: true,
        parallelUploads: 1,// 一次上传的文件数量
        //previewsContainer:"#preview", // 上传图片的预览窗口
        dictDefaultMessage: '拖动文件至此或者点击上传',
        dictMaxFilesExceeded: "您最多只能上传"+ this.maxFiles +"个文件！",
        dictResponseError: '文件上传失败!',
        dictInvalidFileType: "文件类型只能是*.jpg,*.gif,*.png,*.jpeg。",
        dictFallbackMessage: "浏览器不受支持",
        dictFileTooBig: "文件过大上传文件最大支持.",
        dictRemoveLinks: "删除",
        dictCancelUpload: "取消"
    };

    /**
     * 私有方法，初始化 ICheck
     */
    var handlerInitCheckbox = function () {
        // 激活 iCheck
        $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
            checkboxClass: 'icheckbox_minimal-blue',
            radioClass: 'iradio_minimal-blue'
        });

        //获取控制 Checkbox
        _masterCheckbox = $(".checkbox_master");

        //获取全部 Checkbox 集合
        _checkbox = $("tbody").find("[type='checkbox']").not("[disabled]");
    };

    /**
     * Checkbox 的全选功能
     */
    var handlerCheckBoxAll = function () {
        _masterCheckbox.on("ifClicked", function (e) {
            //如果未选中
            if (e.target.checked) {
                _checkbox.iCheck("uncheck");
            }

            //选中状态
            else {
                _checkbox.iCheck("check");
            }
        });
    };

    /**
     * 单个删除
     * @param url
     * @param id
     * @param msg
     */
    var handlerDeleteSingle = function (url, id, msg) {
        // 可选参数
        if (!msg)msg=null;

        //将 ID 放入数组中，以便和批量删除使用
        _idArray = new Array();
        _idArray.push(id);

        $("#modal-message").html(msg == null ? "您确定要删除数据吗？" : msg);
        $("#modal-default").modal("show");
        // 确定删除事件
        $("#btnModalOk").bind("click", function () {
            handlerDeleteData(url);
        });
    }

    /**
     * 批量删除
     * @param url
     */
    var handlerDeleteMulti = function (url) {
        _idArray = new Array();

        //将选中的元素的 ID 放入数组中
        _checkbox.each(function () {
            var _id = $(this).attr("id");
            if (_id != null && _id != "undefine" && $(this).is(":checked")) {
                _idArray.push(_id);
            }
        });

        // 判断用户是否选择数据项
        if (_idArray.length === 0) {
            $("#modal-message").html("您还没有选择任何数据项，请至少选择一项");
        } else {
            $("#modal-message").html("您确定删除数据项吗？");
        }

        //点击删除按钮时，弹出模态框
        $("#modal-default").modal("show");

        //如果用户选择了数据项，则调用删除方法
        $("#btnModalOk").bind("click", function () {
            handlerDeleteData(url);
        });
    };

    /**
     * Ajax 异步删除
     * @param url
     */
    var handlerDeleteData = function (url) {
        $("#modal-default").modal("hide");

        if (_idArray.length > 0) {
            // AJAX 异步删除操作
            setTimeout(function () {
                $.ajax({
                    "url": url,
                    "type": "POST",
                    "data": {"ids" : _idArray.toString()},
                    "dataType": "JSON",
                    "success": function (data) {
                        // 请求成功后，无论是成功或是失败都需要弹出模态框进行提示，所以这里需要先解绑原来的 click 事件
                        $("#btnModalOk").unbind("click");

                        // 请求成功
                        if (data.status === 200) {
                            // 刷新页面
                            $("#btnModalOk").bind("click", function () {
                                window.location.reload();
                            });
                        }

                        // 请求失败
                        else {
                            // 确定按钮的事件改为隐藏模态框
                            $("#btnModalOk").bind("click", function () {
                                $("#modal-default").modal("hide");
                            });
                        }

                        // 因为无论如何都需要提示信息，所以这里的模态框是必须调用的
                        $("#modal-message").html(data.message);
                        $("#modal-default").modal("show");
                    }
                });
            }, 500)
        }
    };

    /**
     * 初始化 DataTables
     * @param url
     * @param columns
     * @returns {jQuery}
     */
    var handlerInitDataTables = function (url, columns) {
        var _dataTable = $('#dataTable').DataTable({
            "paging": true,
            "lengthChange": false,
            "ordering": false,
            "processing": true,
            "searching": false,
            "serverSide": true,
            "deferRender": true,
            "ajax": {
                "url": url,
            },
            "columns": columns,
            "language": {
                "sProcessing": "处理中...",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },
            "drawCallback": function (settings) {
                handlerInitCheckbox();
                handlerCheckBoxAll();
            }
        });

        return _dataTable;
    };

    /**
     * 查看详情
     * @param url
     */
    var handlerShowDetail = function (url) {
        //通过 ajax 请求 html 的方式，将 jsp 装载进模态框
        $.ajax({
            url: url,
            type: "get",
            dataType: "html",
            success: function (data) {
                $("#modal-detail-body").html(data);
                $("#modal-detail").modal("show");
            }
        });
    };

    /**
     * 初始化 ZTree
     * @param url
     * @param autoParam
     * @param callback
     */
    var handlerInitZTree = function (url,autoParam,callback) {
        var setting = {
            view: {
                selectedMulti: false
            },
            async: {
                enable: true,
                url: url,
                autoParam: autoParam
            }
        };

        $.fn.zTree.init($("#myTree"), setting);

        $("#btnModalOk").bind('click', function () {
            var zTree = $.fn.zTree.getZTreeObj("myTree");
            var nodes = zTree.getSelectedNodes();
            //未选择
            if (nodes.length == 0) {
                alert("请选择一个节点");
            }

            //已选择
            else {
                callback(nodes);
            }
        });
    };

    /**
     * 初始化 Dropzone
     * @param opts
     */
    var handlerInitDropzone = function (opts) {
        // 关闭 Dropzone 的自动发现功能
        Dropzone.autoDiscover = false;
        // 继承
        $.extend(defaultDropzoneOpts, opts);
        new Dropzone(defaultDropzoneOpts.id, defaultDropzoneOpts);
    };


    return {
        /**
         * 初始化
         */
        init: function () {
            handlerInitCheckbox();
            handlerCheckBoxAll();
        },

        /**
         * 单个删除
         * @param url
         * @param id
         * @param msg
         */
        deleteSingle: function(url, id, msg){
            handlerDeleteSingle(url, id, msg);
        },

        /**
         * 批量删除
         * @param url
         */
        deleteMulti: function (url) {
            handlerDeleteMulti(url);
        },

        /**
         * 初始化 DataTables
         * @param url
         * @param columns
         * @returns {jQuery}
         */
        initDataTables: function (url, columns) {
            return handlerInitDataTables(url, columns);
        },

        /**
         * 查看详情
         * @param url
         */
        showDetail: function (url) {
            handlerShowDetail(url);
        },

        /**
         * 初始化 ZTree
         * @param url
         * @param autoParam
         * @param callback
         */
        initZTree: function (url, autoParam, callback) {
            handlerInitZTree(url,autoParam,callback);
        },

        /**
         * 初始化 Dropzone
         * @param opts
         */
        initDropzone:function (opts) {
            handlerInitDropzone(opts);
        }

    }
}();

$(document).ready(function () {
    App.init();
});
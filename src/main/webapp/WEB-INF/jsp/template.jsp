<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- JSP ROOT 경로 --%>
<c:set var="JSP_ROOT_PATH" value="/WEB-INF/jsp"/>

<html>
<head>
    <!-- 헤드 메타 정보 -->
    <jsp:include page="${JSP_ROOT_PATH}/commons/meta.jsp"></jsp:include>
    <!-- 상수 -->

    <title>TEMPLATE</title>

    <!-- fonts 로드 -->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">


    <!-- css 로드 -->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">
    <link href="css/custom-color.css" rel="stylesheet">
    <link href="css/bootstrap-treeview.min.css" rel="stylesheet">

</head>
    <body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Sidebar -->
        <jsp:include page="${JSP_ROOT_PATH}/layout/sidebar.jsp" flush="false"/>

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

        <!-- Main Content -->
        <div id="content">

            <!-- Topbar -->
            <jsp:include page="${JSP_ROOT_PATH}/layout/topbar.jsp" flush="false"/>

            <!-- Page Content 
            ###########################################################-->
            <div class="container-fluid">

                여기에 메인 컨텐츠 유아이를 놓으면 됨
                <br> 아래 컴포넌트는 템플릿에 미포함되었으므로 여기에 따로 적어놓았음.
                
                <div class="col-sm-4">
                    <h2>디렉토리 트리뷰 예</h2>
                    <div id="treeview" class=""></div>
                </div>

            </div>
            <!--##########################################################
            /Page Content -->

        </div>
        <!-- /Main Content -->

        <!-- Footer -->
        <jsp:include page="${JSP_ROOT_PATH}/layout/footer.jsp" flush="false"/>

        </div>
        <!-- /Content Wrapper -->

    </div>
    <!-- /Page Wrapper -->

    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>

    <!-- js 로드 -->
    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.min.js"></script>
    <script src="js/bootstrap-treeview.min.js"></script>

    <!-- 트리뷰 스크립트 예 -->
    <script>
        var defaultData = [
          {
            text: 'Parent 1',
            href: '#parent1',
            tags: ['4'],
            nodes: [
              {
                text: 'Child 1',
                href: '#child1',
                tags: ['2'],
                nodes: [
                  {
                    text: 'Grandchild 1',
                    href: '#grandchild1',
                    tags: ['0']
                  },
                  {
                    text: 'Grandchild 2',
                    href: '#grandchild2',
                    tags: ['0']
                  }
                ]
              },
              {
                text: 'Child 2',
                href: '#child2',
                tags: ['0']
              }
            ]
          },
          {
            text: 'Parent 2',
            href: '#parent2',
            tags: ['0']
          },
          {
            text: 'Parent 3',
            href: '#parent3',
             tags: ['0']
          },
          {
            text: 'Parent 4',
            href: '#parent4',
            tags: ['0']
          },
          {
            text: 'Parent 5',
            href: '#parent5'  ,
            tags: ['0']
          }
        ];
        $('#treeview').treeview({
            color: "#428bca",
            showBorder: false,
            selectedColor: "black",
            selectedBackColor: "white",
            expandIcon: 'fa fa-angle-right',
            collapseIcon: 'fa fa-angle-down',
            nodeIcon: 'fa fa-adress-book',
            data: defaultData
        });
    </script>

    </body>
</html>
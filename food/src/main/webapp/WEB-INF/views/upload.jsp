<%@ page import="java.util.Enumeration" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String directory = "C:/Users/admin/git/repository/food/src/main/webapp/resources/test"; // 이클립스 서버쪽에 프로젝트의 해당폴더

        System.out.println(directory); // path를 출력해서 확인(fileFolder 없으면 생성해주자!!!)    



   int size = 1024 * 1024 * 10; // 파일사이즈 최대 크기 10M

   String file = ""; // 중복때문에 뒤에 1,2,3,4 붙은 파일명

   String originFile = ""; // 내가 업로드한 실제 파일명

   

   try {

      // 업로드 파일 정보를 업로드 장소에 크기 및 파일 업로드 수행할 수 있게 함

      MultipartRequest multi = new MultipartRequest(request, directory, size, "UTF-8", new DefaultFileRenamePolicy());

      

      Enumeration files = multi.getFileNames();

      String str = (String)files.nextElement();

      

                // 이 file과 originFile로 S3를 사용하든 DB를 사용하든 하면된다 !!!

      file = multi.getFilesystemName(str);

      originFile = multi.getOriginalFileName(str);

   } catch (Exception e){

      e.printStackTrace();

   }

%>
<script>location.href="m_up"</script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
</body>
</html>
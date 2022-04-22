import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class project {
	public static String filePath;
	public static ArrayList<String> data = new ArrayList<String>(); 
	public static String clinicName, clinicType, clinicPath,RecepPass;
	 
	
	//Scanner use in this class
	public static Scanner reader = new Scanner(System.in);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 readingFileAndPuttingItInArray(gettingFilePath());
	        analysingFileAndWritingIt();

	}
	static String CenterStr(String str, int count) {
		return " ".repeat((count - str.length()) / 2) + str;
	}
	
	public static String gettingFilePath(){
        System.out.print("Please enter file path : ");
        filePath = reader.nextLine();
        System.out.print("Please enter file name : ");
        filePath = filePath + "\\" + reader.nextLine() + ".txt";
        File file = new File(filePath);
        if(file.isFile()){
            return filePath;
        }
        else {
            System.out.println("There is no any file with this name on that path!");
            return gettingFilePath();
        }
    }

	public static void readingFileAndPuttingItInArray(String filePath){
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                data.add(scanner.nextLine());
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Something is wrong, could not read file correctly");
            e.printStackTrace();
        }
    }
	
	public static void analysingFileAndWritingIt(){
        for (int i = 0; i < data.size(); i++) {
            //delete space in first of all line
            data.set(i, data.get(i).strip());

            switch (gettingTag(data.get(i))){
                case "*" :
                    gettingClinicNameAndType(data.get(i));
                    creatFolderForClinic(clinicName, clinicType);
                    break;
                case "&" :
                	SectionInfo(data.get(i));
                    break;
                case "M" :
                	ClinicInfo(data.get(i));
                    break;
                case "R" :
                	ReceptionistInfo(data.get(i));
                    
                    break;
                case "@" :
                	DoctorInfo(data.get(i));
                           // analysingAndWritingHistory(data.get(i));
                    break;
                case "#" :
                	PatientsInfo(data.get(i));
                    break;
                case "+" :
                    //isHistoryStart = true;
                    break;
                default :
                   // isHistoryStart = false;       //in all situation means history finish
                    break;
            }

        }
    }
	
	public static String gettingTag(String s){
        //use that algorithm because if string empty can not use charAt
        if(s.isEmpty()){
            return "empty";
        }
        else {
            return s.charAt(0)+"";
        }
    }
	
	public static void gettingClinicNameAndType(String data){
        String[] substrings = data.split(" - ");
        substrings[0] = substrings[0].substring(2);   //delete ** in first of it
        clinicName = substrings[0].strip();
        clinicType = substrings[1].strip();
    }
	
	public static void creatFolderForClinic(String clinicName , String clinicType){
        File file = new File(filePath);
        String fileParent = file.getParent();
        fileParent = fileParent+ "\\" + clinicName + "\\" + clinicType;
        File file1 = new File(fileParent);
        //checking is that clinic exited or not
        if (file1.exists()){
            System.out.println(clinicName + "/" + clinicType + " Clinic has already been analyed");
        }
        clinicPath = fileParent;
        boolean bool = file1.mkdirs();
    }
	
	public static void SectionInfo(String data){
        String[] substrings = data.split("-");
        substrings[0] = substrings[0].substring(1);     //delete & in first of it
        substrings[1] = substrings[1].strip();            //delete space in it
        substrings[2] = substrings[2].strip();            //delete space in it
        substrings[3] = substrings[3].strip();            //delete space in it
        substrings[3] = substrings[3].substring(1);       //delete @ in first of it
        for (int i = 4; i <substrings.length; i++) {
            substrings[i] = substrings[i].strip();
            substrings[i] = substrings[i].substring(1);
        }
        // filling Null in empty elements
        for (int i = 0; i < 4; i++) {
            if (substrings[i].isEmpty()){
                substrings[i] = "Null";
            }
        }
        //*********************
        /*checkBox
        index 0 = sectionName , index 1 =sectionType , index 2 =recepName
        index 3 = doctorName , index 4and... = patients
         */
        //*********************
        String flagPath = clinicPath + "\\" + "SectionInfo.txt";
      {
            try {
                FileWriter preWriter = new FileWriter(flagPath , true);
                preWriter.write("\n\n   ***   ***   ***\n\n");
                preWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter writer = new FileWriter(flagPath, true);
            writer.write("SectionName: " + substrings[0]);
            writer.write("\nSectionType: " + substrings[1]);
            writer.write("\nRecepName: " + substrings[2]);
            writer.write("\nDoctorName: " + substrings[3]);
            //the algorithm for write Patients Name is less compliction
            writer.write("\nPatientNmaes: ");
            if (substrings.length>4){
                writer.write(" " + substrings[4]);
            } else writer.write("Null");
            for (int i = 5; i <substrings.length; i++) {
                writer.write(", " + substrings[i]);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	

	public static void ClinicInfo(String data){
		String[] substrings = data.split("-");
		substrings[0] = substrings[0].substring(2);    
		substrings[1] = substrings[1].strip();                       
		substrings[2] = substrings[2].strip();            
		substrings[3] = substrings[3].strip();            
		//*********************
		/*checkBox
		index 0 = managerName , index 1 =managerSallary , index 2 =managerUsername
		index 3 = managerPassword
		 */
		//*********************
		String flagPath = clinicPath + "\\" + "ClinicInfo.txt";
		 
	
		 try {
	            FileWriter writer = new FileWriter(flagPath, true);
	           writer.write("|   ClinicName   |" );
	            writer.write("   ClinicType   |");
	            writer.write("   ManagerName   |" );
	            writer.write("   ManagerSallary   |");
	           writer.write("   ManagerUser   |");
	           writer.write("   ManagerPass   |");
	          
	        
	            
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		try {
			FileWriter writer = new FileWriter(flagPath, true);
			//formatting the data
			clinicName = addSpaceInFirstOfString(clinicName);
			clinicType = addSpaceInFirstOfString(clinicType);
			for (int i = 0; i < 4; i++) {
				substrings[i] = addSpaceInFirstOfString(substrings[i]);
			}
			String format = String.format("|%-16s|%-16s|%-17s|%-20s|%-17s|%-17s|",clinicName,clinicType,substrings[0],
					substrings[1],substrings[2],substrings[3]);
			writer.write("\n");
			writer.write(format);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static String addSpaceInFirstOfString(String string){
        int count = (15 - string.length())/2; 
        String space = "";
        int spaceCount=4;
        for (int i = 0; i < spaceCount; i++) {
            space = space + " ";
        }
        space = space.concat(string);
        return space;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		public static void ReceptionistInfo(String data)
    {
		String[] subStrings = data.split("-");
		
			subStrings [0]= subStrings [0].substring(2);
			subStrings[1] = subStrings[1].strip();            //delete space in it
			subStrings[2] = subStrings[2].strip();            //delete space in it
			subStrings[3] = subStrings[3].strip();            //delete space in it
			
			
	
		          
	
		//checkBox
		//index 0 = ReceptionistName , index 1 = RecepSectionName , index 2 =RecepSallary
		//index 3 = RecepUser index 4 = RecepPass
	 
			 for (int i = 0; i <subStrings.length; i++) {
				 subStrings[i] = subStrings[i].strip();
		        
		        }
		
		 
		 // filling Null in empty elements
	        for (int i = 0; i < subStrings.length; i++) {
	            if (subStrings[i].isEmpty()){
	            	subStrings[i] = "Null";
	            }
	        }
		 String flagPath = clinicPath + "\\" + "ReceptionistInfo.txt";
		    boolean IsNeedToWriteFirstLine =true;
			
				if (IsNeedToWriteFirstLine) {
		 try {
	            FileWriter writer = new FileWriter(flagPath,true);
	            writer.write("|   RecepName   |" );
	            writer.write("   RecepSection   |");
	            writer.write("   RecepSallary   |");
	            writer.write("   RecepUser   |" );
	            

	            IsNeedToWriteFirstLine =false;
	            
	            for (int i = 0; i < 4; i++) {
					subStrings[i] = addSpaceInFirstOfString(subStrings[i]);
				}
	           
	           String format = String.format("|%-15s|%-18s|%-18s|%-15s|",subStrings[0],subStrings[1],subStrings[2],
						subStrings[3]);
				writer.write("\n");
				writer.write(format);
				
				
	       
	            writer.close();
	        } catch (IOException e) { 
	            e.printStackTrace();
	        }
				}

		
   } 
		 public static void DoctorInfo(String data) {
		String[] subStrings = data.split("-");
		 
			  subStrings [0]= subStrings [0].substring(1);
				subStrings[1] = subStrings[1].strip();            //delete space in it
				subStrings[2] = subStrings[2].strip();            //delete space in it
				subStrings[3] = subStrings[3].strip();            //delete space in it
			    subStrings[4] = subStrings[4].strip();            //delete space in it
				subStrings[5] = subStrings[5].strip();            //delete space in it
				subStrings[6] = subStrings[6].strip();            //delete space in it
		  
		
		 for (int i = 0; i <subStrings.length; i++) {
			 subStrings[i] = subStrings[i].strip();
	        
	        }
		 // filling Null in empty elements
	        for (int i = 0; i < subStrings.length; i++) {
	            if (subStrings[i].isEmpty()){
	            	subStrings[i] = "Null";
	            }
	        }
	        String flagPath = clinicPath + "\\" + "DoctorInfo.txt";
	        boolean IsNeedToWriteFirstLine =true;
			
			if (IsNeedToWriteFirstLine) {
	        try {
	            FileWriter writer = new FileWriter(flagPath,IsNeedToWriteFirstLine);
	           writer.write("|   DoctorName   |" );
	            writer.write("   DoctorType   |" );
	            writer.write("   DoctorSection   |" );
	            writer.write("   DoctorSallary   |" );
	            writer.write("   DoctorVisit   |" );
	            writer.write("   DoctorPass   |" );
	          writer.write("   DoctorUser   |" );
	 	       IsNeedToWriteFirstLine = false;
	          
	          for (int i = 0; i < 7; i++) {
					subStrings[i] = addSpaceInFirstOfString(subStrings[i]);
				}
				String format = String.format("|%-16s|%-16s|%-19s|%-19s|%-17s|%-16s|%-16s|",subStrings[0],subStrings[1],subStrings[2],subStrings[3],subStrings[4],subStrings[5],subStrings[6]);
				writer.write("\n");
				writer.write(format);
	       
	  
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
			}
	        
	  
	}
public static void PatientsInfo(String data) {
	String[] subStrings = data.split("-");
	 
	  subStrings [0]= subStrings [0].substring(1);
		subStrings[1] = subStrings[1].strip();            //delete space in it
		subStrings[2] = subStrings[2].strip();            //delete space in it
		subStrings[3] = subStrings[3].strip();            //delete space in it
		subStrings[4] = subStrings[4].strip();            //delete space in it
		 for (int i = 4; i <subStrings.length; i++) {
			 subStrings[i] = subStrings[i].strip();
	        
	        }
		 // filling Null in empty elements
	        for (int i = 0; i < 4; i++) {
	            if (subStrings[i].isEmpty()){
	            	subStrings[i] = "Null";
	            }
	        }
	        String flagPath = clinicPath + "\\" + "PatientsInfo.txt";
	        boolean IsNeedToWriteFirstLine =true;
		
			if (IsNeedToWriteFirstLine) {
				try { 
			            FileWriter writer = new FileWriter(flagPath,true);
			           writer.write("|   PatientName   |" );
			            writer.write("   SicknessType   |" );
			            writer.write("   PatientsWallet   |" );
			            writer.write("   PatientUser   |" );
			            writer.write("   PatientPass   |" );
			            
			            IsNeedToWriteFirstLine = false;
			            
			            for (int i = 0; i < 5; i++) {
							subStrings[i] = addSpaceInFirstOfString(subStrings[i]);
						}
			            
			            
			        	String format = String.format("|%-17s|%-18s|%-20s|%-17s|%-17s|",subStrings[0],subStrings[1],subStrings[2],
								subStrings[3],subStrings[4]);
						writer.write("\n");
						writer.write(format);
			     
			            writer.close();
			
			}
		 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			}
	       

}
	
}

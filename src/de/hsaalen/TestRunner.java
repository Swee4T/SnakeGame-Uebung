package de.hsaalen;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
   public static void main(String[] args) 
   {
      Result result = JUnitCore.runClasses( PanelTest.class, SnakeTest.class );
		
	  int i = 0;
      for ( Failure failure : result.getFailures() ) {
         System.out.println( "Failure" + " " + i + " " + failure.getDescription() );
         System.out.println( "  " + failure.getTrace() );
		 
		 i++;
      }
   }
}
#include <stdio.h>
#include <stdlib.h>

// Reads a given file for specified keywords given in the arguments
// a.exe (number of lines after keyword hit) (keywords...)
// Will read a given file and search for keywords
// x amount of lines will be printed to the second file after a keyword has been hit
// will create the second file with the copied text

int main(int argc, char *argv[])
{
  //establish variables and pointers
  FILE *fptr1, *fptr2;
  char filename[100], c;
  int i;

  //number of lines copied after keyword hit
  int noln = atoi(argv[1]);
  
  int match = 0;

  //system prompt for two files
  printf("Enter the filename for reading \n");
  scanf("%s", filename);

  fptr1 = fopen(filename, "r");
  if (fptr1 == NULL)
    {
      printf("Cannot open file %s \n", filename);
      exit(0);
    }

  printf("Enter a filename for writing bruh \n");
  scanf("%s", filename);

  fptr2 = fopen(filename, "w");
  if (fptr2 == NULL)
    {
      printf("Cannot open file %s \n");
      exit(0);
    }

  //size of the lines read in the file
  char line[1024];

  //reads the file line by line in while loop
  while (fgets(line, sizeof(line), fptr1))
    {
      //starts looking at keywords given from the second argument and beyond
      i = 2;

      //checks to see if there was a keyword match in the within the last few lines and prints the current line if there was a recent match
      if (match > 0)
	{
	  printf("%s",line);
	  fputs(line, fptr2);
          match--;
	}

      //checks current line for keywords
      else
	{
      while (i < argc)
	{
	  //scans all of the keywords and breaks if there is at least 1 match
	  if (strstr(line, argv[i]))
	    {
	      printf("%s",line);
	      fputs(line, fptr2);
	      match = noln;
	      break;
	    }
	  i++;
	}
	}
    }

  printf("\nContents copied to %s", filename);

  fclose(fptr1);
  fclose(fptr2);
  return 0;
}
